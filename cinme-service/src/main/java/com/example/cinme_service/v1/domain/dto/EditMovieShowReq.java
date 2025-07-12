package com.example.cinme_service.v1.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EditMovieShowReq {

  private Long movieId;
  private Long hallId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
}
