package com.example.cinme_service.v1.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
@Table(name = "m2_seat_reservations")
public class SeatReservation {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne(fetch = LAZY)
  private SeatBooking seatBooking;
  @ManyToOne(fetch = LAZY)
  private Seat seat;

  public SeatReservation(SeatBooking seatBooking, Seat seat) {
    this.seatBooking = seatBooking;
    this.seat = seat;
  }
}