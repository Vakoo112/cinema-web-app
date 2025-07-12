package com.example.cinme_service.v1.service;

import com.example.cinme_service.v1.domain.Seat;
import com.example.cinme_service.v1.domain.SeatBooking;
import com.example.cinme_service.v1.domain.SeatReservation;
import com.example.cinme_service.v1.domain.dto.EditSeatBookingReq;
import com.example.cinme_service.v1.domain.dto.SeatBookResp;
import com.example.cinme_service.v1.domain.dto.SeatBookingSearch;
import com.example.cinme_service.v1.domain.enums.BookingStatus;
import com.example.cinme_service.v1.domain.mapper.SeatBookingMapper;
import com.example.cinme_service.v1.kafka.KafkaMessageProducer;
import com.example.cinme_service.v1.kafka.NotificationData;
import com.example.cinme_service.v1.kafka.PaymentData;
import com.example.cinme_service.v1.repository.SeatBookingRepo;
import com.example.cinme_service.v1.repository.SeatBookingSpec;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_utils.v1.exception.ForbiddenException;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static com.example.cinme_service.v1.exception.ErrorKeyword.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatBookingService {

  private final SeatService seatService;
  private final MovieShowService movieShowService;
  private final SeatBookingRepo seatBookingRepo;
  private final SeatBookingMapper seatBookingMapper;
  private final KafkaMessageProducer kafkaMessageProducer;

  @Transactional
  public SeatBookResp createPendingBooking(List<Long> seatIds, Long movieShowId, Long userId) {
    if (seatIds.size() > 4)
      throw new ForbiddenException(MAX_SEAT_RESTRICTION);

    var movieShow = movieShowService.getActiveMovieShowById(movieShowId);
    var hallId = movieShow.getHall().getId();

    var seats = seatService.getAvailableSeatsForBooking(seatIds, hallId, movieShowId);

    if (seats.size() != seatIds.size()) {
      throw new ForbiddenException(SEAT_UNAVAILABLE);
    }

    var booking = new SeatBooking();
    booking.setMovieShow(movieShow);
    booking.setStatus(BookingStatus.PENDING);
    booking.setExpiresAt(LocalDateTime.now().plusMinutes(3));

    for (Seat seat : seats) {
      var reservation = new SeatReservation();
      reservation.setSeat(seat);
      reservation.setSeatBooking(booking);
      booking.getReservations().add(reservation);
    }

    seatBookingRepo.saveAndFlush(booking);
    return seatBookingMapper.toSeatBookResp(booking);
  }

  @Transactional
  public void expirePendingBookings() {
    // Find all bookings with PENDING status created today and expired
    var expiredBookings = seatBookingRepo.findAllByStatusAndCreatedDateAfterAndExpiresAtBefore(
        BookingStatus.PENDING, LocalDate.now().atStartOfDay(), LocalDateTime.now());

    for (SeatBooking booking : expiredBookings) {
      if (booking.getPaymentId() == null) {
        booking.setStatus(BookingStatus.UNPAID);
        // Optionally clear reservations or do other logic
      }
    }
    seatBookingRepo.saveAll(expiredBookings);
  }

  @Transactional
  public SeatBookResp edit(long id, EditSeatBookingReq req) {
    var seatBooking = findById(id);

    // Fetch updated seats and movieShow
    var movieShow = movieShowService.getActiveMovieShowById(req.getMovieShowId());
    var hallId = movieShow.getHall().getId();
    var seats = seatService.getAvailableSeatsForBooking(req.getSeatIds(), hallId, req.getMovieShowId());

    // Optionally update MovieShow if changed
    if (!seatBooking.getMovieShow().getId().equals(req.getMovieShowId())) {
      var movieShowChange = movieShowService.getActiveMovieShowById(req.getMovieShowId());
      seatBooking.setMovieShow(movieShowChange);
    }

    // Clear existing reservations (Hibernate will remove them due to orphanRemoval = true)
    seatBooking.getReservations().clear();

    // Add new reservations
    seats.forEach(seat -> {
      SeatReservation reservation = new SeatReservation();
      reservation.setSeat(seat);
      reservation.setSeatBooking(seatBooking);
      seatBooking.getReservations().add(reservation);
    });

    seatBookingRepo.flush();

    return seatBookingMapper.toSeatBookResp(seatBooking);
  }


  @Transactional
  public void updatePaymentStatusDetails(PaymentData req) {
    var seatBooking = findById(req.getSeatBookingId());
    if (req.getPaymentStatus() == PaymentData.PaymentStatus.SUCCESS) {
      var uniqueCode = generateMVCode();
      seatBooking.setStatus(BookingStatus.BOOKED);
      seatBooking.setUniqueCode(uniqueCode);
      seatBooking.setPaymentId(req.getPaymentId());

      var notifData = new NotificationData(
          seatBooking.getCreatedBy(),
          seatBooking.getMovieShow().getId(),
          uniqueCode,
          "PAYMENT_RESULT",
          Map.of("uniqueCode", uniqueCode)
      );
      kafkaMessageProducer.sendNotificationMessage(notifData);
    }
  }

  public static String generateMVCode() {
    // Get a random UUID and take the first 8 characters for brevity
    String randomPart = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
    return "MV" + randomPart;
  }

  public PageResp<List<SeatBookResp>> search(SeatBookingSearch req, Pageable pageable) {
    var spec = new SeatBookingSpec(req);
    var seatBookingPage = seatBookingRepo.findAll(spec, pageable);
    var seatBookingRespList = seatBookingMapper.toSeatBookRespList(seatBookingPage.getContent());
    var pageResp = new PageResp.PageSizeResp(
        seatBookingPage.getTotalElements(),
        seatBookingPage.getTotalPages(),
        seatBookingPage.getSize(),
        seatBookingPage.getNumber());
    return new PageResp<>(seatBookingRespList, pageResp);
  }

  public SeatBookResp get(Long id) {
    var seatBooking = findById(id);
    return seatBookingMapper.toSeatBookResp(seatBooking);
  }


  public SeatBooking findById(long id) {
    return seatBookingRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(SEAT_BOOKING_NOT_FOUND));
  }
}
