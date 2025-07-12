package com.example.ticket_um.v1.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditRoleReq {

  @NotBlank
  private String name;
  private String description;

  public String getName() {
    return name.strip();
  }

  public String getDescription() {
    return description != null ? description.strip() : null;
  }
}
