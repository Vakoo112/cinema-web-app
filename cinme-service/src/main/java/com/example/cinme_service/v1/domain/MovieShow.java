package com.example.cinme_service.v1.domain;

import com.example.ticket_utils.v1.domain.model.Extendable;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m2_movie_shows")
public class MovieShow extends Extendable {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "movie_id")
  private Movie movie;
  @ManyToOne
  @JoinColumn(name = "hall_id")
  private Hall hall;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private boolean active = true;
}