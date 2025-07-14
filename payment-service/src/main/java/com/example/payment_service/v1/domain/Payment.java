package com.example.payment_service.v1.domain;

import com.example.payment_service.v1.domain.enums.PaymentStatus;
import com.example.ticket_utils.v1.domain.model.Extendable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m3_payments")
public class Payment extends Extendable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long seatBookingId;
  private Integer amount;
  private String currency;
  private String stripePaymentIntentId;
  @Enumerated(EnumType.STRING)
  private PaymentStatus status;
}
