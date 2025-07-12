package com.example.ticket_um.v1.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthReq {

  @NotNull
  private String username;
  @NotNull
  private String password;
}
