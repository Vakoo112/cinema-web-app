package com.example.cinme_service.v1.domain.dto;

import com.example.cinme_service.v1.domain.enums.SeatCondition;
import lombok.Data;

@Data
public class EditSeatReq {

  private String seatNumber;
  private String colorCode;
  private SeatCondition seatCondition;
}
