package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.SeatBooking;
import com.example.cinme_service.v1.domain.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.time.LocalDateTime;
import java.util.List;

public interface SeatBookingRepo extends JpaRepository<SeatBooking, Long>, JpaSpecificationExecutor<SeatBooking> {

  List<SeatBooking> findAllByStatusAndCreatedDateAfterAndExpiresAtBefore(BookingStatus status, LocalDateTime createdAfter, LocalDateTime expiresBefore);
}
