package com.example.cinme_service.v1.domain;

import com.example.cinme_service.v1.domain.enums.AgeRestriction;
import com.example.ticket_utils.v1.domain.model.Extendable;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m2_movies")
public class Movie extends Extendable {

  @Id
  @GeneratedValue
  @Column(name = "movie_id")
  private Long id;
  private String title;
  @Column(length = 1000)
  private String description;
  private int durationInMinutes;
  private LocalDateTime releaseDate;
  private LocalDateTime showStartDate;
  private LocalDateTime showEndDate;
  private String imdbId;
  private String imdbRating;
  @Enumerated(EnumType.STRING)
  private AgeRestriction ageRestriction;
  private boolean active = true;
}