package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface SeatRepo extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {

  @Query("select count(s) from Seat s where s.hall.id = :hallId and s.deleted = false")
  long countActiveSeatsByHallId(Long hallId);

  @Query("""
         select s from Seat s
          where s.id in :seatIds
            and s.deleted = false
            and s.hall.id = :hallId
            and s.id not in (
         select sr.seat.id from SeatReservation sr
          where sr.seatBooking.movieShow.id = :movieShowId
            and sr.seatBooking.status in ('BOOKED', 'PENDING'))""")
  List<Seat> findAvailableSeatsForBooking(List<Long> seatIds, Long hallId, Long movieShowId);

  @Query("""
         select s from Seat s
          where s.hall.id = :hallId
            and s.deleted = false
            and s.id not in (
         select sr.seat.id from SeatReservation sr
          where sr.seatBooking.movieShow.id = :movieShowId
            and sr.seatBooking.status in ('BOOKED', 'PENDING') )""")
  List<Seat> findAvailableSeatsByMovieShow(Long hallId, Long movieShowId);

  List<Seat> findAllByHallIdAndDeletedFalse(Long hallId);

  Optional<Seat> findByIdAndDeletedFalse(Long seatId);

  List<Seat> findAllByIdInAndDeletedFalse(List<Long> ids);

  @Query(value = "select m2_seat_pk_gen.nextval from dual", nativeQuery = true)
  Long getNewId();
}
