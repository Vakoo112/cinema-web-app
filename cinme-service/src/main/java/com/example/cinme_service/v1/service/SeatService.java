package com.example.cinme_service.v1.service;

import com.example.cinme_service.v1.domain.Hall;
import com.example.cinme_service.v1.domain.Seat;
import com.example.cinme_service.v1.domain.dto.SeatReq;
import com.example.cinme_service.v1.domain.dto.SeatResp;
import com.example.cinme_service.v1.domain.dto.EditSeatReq;
import com.example.cinme_service.v1.domain.dto.SeatSearch;
import com.example.cinme_service.v1.domain.mapper.SeatMapper;
import com.example.cinme_service.v1.repository.SeatRepo;
import com.example.cinme_service.v1.repository.SeatSpec;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_utils.v1.exception.ForbiddenException;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import static com.example.cinme_service.v1.exception.ErrorKeyword.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SeatService {

  private final SeatRepo seatRepo;
  private final HallService hallService;
  private final SeatMapper seatMapper;
  private final MovieShowService movieShowService;

  @Transactional
  public SeatResp add(long hallId, SeatReq req) {
    var hall = hallService.findById(hallId);
    var seat = add(hall, req);
    return seatMapper.toSeatResp(seat);
  }

  @Transactional
  public Seat add(Hall hall, SeatReq req) {
    if (seatRepo.existsById(req.getId())) {
      throw new ForbiddenException(CAN_NOT_ADD_SEAT);
    }

    var currentSeatCount = seatRepo.countActiveSeatsByHallId(hall.getId());
    if (currentSeatCount >= hall.getCapacity()) {
      throw new ForbiddenException(HALL_IS_FULL);
    }

    var seat = seatMapper.toSeat(req);
    seat.setHall(hall);
    seat.setSeatCondition(req.getSeatCondition());
    return seatRepo.save(seat);
  }

  @Transactional
  public SeatResp edit(long id, EditSeatReq req) {
    var seat = findById(id);
    seatMapper.update(req, seat);
    seatRepo.flush();
    return seatMapper.toSeatResp(seat);
  }

  @Transactional
  public void del(long id) {
    var seat = findById(id);
    seat.setDeleted(true);
  }

  public List<SeatResp> getAllSeatsByHall(Long hallId) {
    var seats = seatRepo.findAllByHallIdAndDeletedFalse(hallId);
    return seatMapper.toSeatRespList(seats);
  }

  public List<Seat> getAvailableSeatsForBooking(List<Long> seatIds, Long hallId, Long movieShowId) {
    return seatRepo.findAvailableSeatsForBooking(seatIds, hallId, movieShowId);
  }

  public List<SeatResp> getAvailSeats(Long movieShowId) {
    var movieShow = movieShowService.findById(movieShowId);
    var hallId = movieShow.getHall().getId();

    var availableSeats = seatRepo.findAvailableSeatsByMovieShow(hallId, movieShowId);
    return seatMapper.toSeatRespList(availableSeats);
  }

  public SeatResp get(Long id) {
    var seat = findById(id);
    return seatMapper.toSeatResp(seat);
  }

  public Long getPrice(List<Long> seatIds) {
    List<Seat> seats = seatRepo.findAllByIdInAndDeletedFalse(seatIds);
    return seats.stream()
        .map(Seat::getAmount)
        .filter(Objects::nonNull)
        .reduce(0L, Long::sum);
  }

  public PageResp<List<SeatResp>> search(SeatSearch req, Pageable pageable) {
    var spec = new SeatSpec(req);
    var seatPage = seatRepo.findAll(spec, pageable);
    var SeatRespList = seatMapper.toSeatRespList(seatPage.getContent());
    var pageResp = new PageResp.PageSizeResp(
        seatPage.getTotalElements(),
        seatPage.getTotalPages(),
        seatPage.getSize(),
        seatPage.getNumber());
    return new PageResp<>(SeatRespList, pageResp);
  }

  public Long getNewId() {
    return seatRepo.getNewId();
  }

  public Seat getActive(Long seatId) {
    return seatRepo.findByIdAndDeletedFalse(seatId)
        .orElseThrow(() -> new ForbiddenException(MOVIE_SHOW_NOT_FOUND_OR_NOT_EXISTS));
  }

  public Seat findById(long id) {
    return seatRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(SEAT_NOT_FOUND));
  }
}
