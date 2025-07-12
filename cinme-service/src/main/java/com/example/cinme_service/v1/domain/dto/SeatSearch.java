package com.example.cinme_service.v1.domain.dto;

import com.example.cinme_service.v1.domain.enums.SeatCondition;
import lombok.Data;

@Data
public class SeatSearch {

  private Long seatId;
  private Long hallId;
  private String seatNumber;
  private String colorCode;
  private SeatCondition seatCondition;
}
