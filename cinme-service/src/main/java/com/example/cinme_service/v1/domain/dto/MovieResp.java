package com.example.cinme_service.v1.domain.dto;

import com.example.cinme_service.v1.domain.enums.AgeRestriction;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovieResp {

  private Long id;
  private LocalDateTime createdDate;
  private Long createdBy;
  private String title;
  private String description;
  private int durationInMinutes;
  private LocalDateTime releaseDate;
  private LocalDateTime showStartDate;
  private LocalDateTime showEndDate;
  private String imdbId;
  private String imdbRating;
  private AgeRestriction ageRestriction;
  private boolean active;
}
