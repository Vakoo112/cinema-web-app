package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SeatReservationRepo extends JpaRepository<SeatReservation, Long> {

  @Query("""
         select sr.seat.id
           from SeatReservation sr
          where sr.seatBooking.movieShow.id = :movieShowId
            and sr.seatBooking.status in ('BOOKED', 'PENDING')""")
  List<Long> findBookedSeatIdsByMovieShowId(Long movieShowId);
}
