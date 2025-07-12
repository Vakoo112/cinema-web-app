package com.example.cinme_service.v1.domain.dto;

import com.example.cinme_service.v1.domain.enums.SeatCondition;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SeatResp {

  private Long id;
  private LocalDateTime createdDate;
  private Long createdBy;
  private HallResp hall;
  private String seatNumber;
  private String colorCode;
  private SeatCondition seatCondition;
}
