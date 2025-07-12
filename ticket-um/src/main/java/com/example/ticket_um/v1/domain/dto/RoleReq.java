package com.example.ticket_um.v1.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleReq {

  @NotBlank
  private String name;
  private String description;
  @NotNull
  private Long groupId;

  public String getName() {
    return name.strip();
  }

  public String getDescription() {
    return description != null ? description.strip() : null;
  }
}
