package com.example.cinme_service.v1.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovieShowReq {

  private Long movieId;
  private Long hallId;
  private LocalDateTime startTime;
}
