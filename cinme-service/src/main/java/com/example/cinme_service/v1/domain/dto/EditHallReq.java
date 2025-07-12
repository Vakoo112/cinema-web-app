package com.example.cinme_service.v1.domain.dto;

import lombok.Data;

@Data
public class EditHallReq {

  private String name;
  private String type;
  private String location;
  private Integer capacity;
}
