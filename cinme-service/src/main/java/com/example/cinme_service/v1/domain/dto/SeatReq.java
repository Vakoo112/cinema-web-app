package com.example.cinme_service.v1.domain.dto;

import com.example.cinme_service.v1.domain.enums.SeatCondition;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeatReq {

  @NotNull
  private Long id;
  private String seatNumber;
  private String colorCode;
  private SeatCondition seatCondition;
}
