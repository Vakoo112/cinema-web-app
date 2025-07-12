package com.example.cinme_service.v1.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HallResp {

  private Long id;
  private LocalDateTime createdDate;
  private Long createdBy;
  private String name;
  private String type;
  private String location;
  private Integer capacity;
}
