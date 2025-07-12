package com.example.ticket_um.v1.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupReq {

  @NotBlank
  private String name;
  private String description;
  @NotBlank
  private String keyword;

  public String getName() {
    return name.strip();
  }

  public String getDescription() {
    return description != null ? description.strip() : null;
  }

  public String getKeyword() {
    return keyword.strip();
  }
}
