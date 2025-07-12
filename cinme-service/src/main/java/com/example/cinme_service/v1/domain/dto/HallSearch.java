package com.example.cinme_service.v1.domain.dto;

import lombok.Data;

@Data
public class HallSearch {

  private Long hallId;
  private String name;
  private String type;
  private String location;
  private Integer capacityFrom;
  private Integer capacityTo;
}
