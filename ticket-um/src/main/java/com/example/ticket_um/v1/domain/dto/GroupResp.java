package com.example.ticket_um.v1.domain.dto;

import lombok.Data;

@Data
public class GroupResp {

  private Long id;
  private boolean active;
  private String name;
  private String description;
  private String keyword;
}
