package com.example.cinme_service.v1.domain.dto;

import com.example.cinme_service.v1.domain.enums.AgeRestriction;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MovieSearch {

  private Long movieId;
  private String title;
  private String description;
  private LocalDate releaseDateFrom;
  private LocalDate releaseDateTo;
  private String imdbId;
  private String imdbRating;
  private AgeRestriction ageRestriction;
}
