package com.example.cinme_service.v1.kafka;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("PaymentData")
public class PaymentData extends KafkaMessageData {

  public enum PaymentStatus {

    SUCCESS,
    FAILED
  }

  private Long seatBookingId;
  private Long paymentId;
  private Long userId;
  private PaymentStatus paymentStatus;
}