package com.example.payment_service.v1.kafka;

import com.example.payment_service.v1.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("PaymentData")
public class PaymentData extends KafkaMessageData {

  private Long seatBookingId;
  private Long paymentId;
  private Long userId;
  private PaymentStatus paymentStatus;
}