package com.example.cinme_service.v1.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovieShowResp {

  private Long id;
  private LocalDateTime createdDate;
  private Long createdBy;
  private MovieResp movie;
  private HallResp hall;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private boolean active;
}
