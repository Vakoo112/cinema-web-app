package com.example.cinme_service.v1.service;

import com.example.cinme_service.v1.domain.SeatReservation;
import com.example.cinme_service.v1.repository.SeatReservationRepo;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.example.cinme_service.v1.exception.ErrorKeyword.SEAT_RESERVATION_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatReservationService {

  private final SeatReservationRepo seatReservationRepo;

  public List<Long> findBookedSeatIds(Long movieShowId) {
    return seatReservationRepo.findBookedSeatIdsByMovieShowId(movieShowId);
  }

  public SeatReservation findById(long id) {
    return seatReservationRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(SEAT_RESERVATION_NOT_FOUND));
  }
}
