package com.example.payment_service.v1.domain.dto;

import lombok.Data;

@Data
 public class CreatePaymentReq {

  private Long seatBookingId;
  private Integer amount;
  private String currency;
}