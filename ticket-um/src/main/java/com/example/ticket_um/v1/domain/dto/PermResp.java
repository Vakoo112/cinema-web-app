package com.example.ticket_um.v1.domain.dto;

import lombok.Data;

@Data
public class PermResp {

  private Long id;
  private String name;
  private Boolean active;
  private String permDescription;
}
