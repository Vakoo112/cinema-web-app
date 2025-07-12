package com.example.cinme_service.v1.domain;

import com.example.cinme_service.v1.domain.enums.SeatCondition;
import com.example.ticket_utils.v1.domain.model.Extendable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
@Table(name = "m2_seats")
public class Seat extends Extendable {

  @Id
  @Column(name = "seat_id")
  private Long id;
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "hall_id")
  private Hall hall;
  private String seatNumber;
  private String colorCode;
  private Boolean deleted = false;
  @Enumerated(EnumType.STRING)
  private SeatCondition seatCondition;
  private Long amount;

  //Delete
//  public Seat(Hall hall, String seatNumber, String colorCode, SeatCondition seatCondition) {
//    this.hall = hall;
//    this.seatNumber = seatNumber;
//    this.colorCode = colorCode;
//    this.seatCondition = seatCondition;
//  }
}