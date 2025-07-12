package com.example.cinme_service.v1.domain;

import com.example.cinme_service.v1.domain.enums.BookingStatus;
import com.example.ticket_utils.v1.domain.model.Extendable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.CascadeType.ALL;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m2_seat_bookings")
public class SeatBooking extends Extendable {

  @Id
  @GeneratedValue
  @Column(name = "seat_book_id")
  private Long id;
  @ManyToOne
  private MovieShow movieShow;
  @Enumerated(EnumType.STRING)
  private BookingStatus status;
  private Long paymentId;
  private LocalDateTime expiresAt;
  private String uniqueCode;
  @OneToMany(mappedBy = "seatBooking", cascade = ALL, orphanRemoval = true)
  private List<SeatReservation> reservations = new ArrayList<>();
}
