package com.example.cinme_service.v1.domain.dto;

import com.example.cinme_service.v1.domain.enums.BookingStatus;
import com.example.cinme_service.v1.domain.enums.SeatCondition;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SeatBookResp {

  private Long id;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long createdBy;
  private MovieShowResp movieShow;
  private BookingStatus status;
  private Long paymentId;
  private LocalDateTime expiresAt;
  private List<ReservationResp> reservations;

  @Data
  public static class ReservationResp {

    private Long id;
    private Long seatId;
    private String seatNumber;
    private String colorCode;
    private SeatCondition seatCondition;
  }
}
