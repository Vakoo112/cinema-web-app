package com.example.cinme_service.v1.domain.dto;

import com.example.cinme_service.v1.domain.enums.BookingStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SeatBookingSearch {

  private Long movieShowId;
  private LocalDate bookedFrom;
  private LocalDate bookedTo;
  private BookingStatus status;
}
