package com.example.cinme_service.v1.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeatBookingReq {

  private List<Long> seatIds;
  private Long movieShowId;
  private Long userId;
}