package com.example.ticket_um.v1.domain.dto;

import com.example.ticket_um.v1.domain.model.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResp {

  private String token;
  private TokenType tokenType;
  private UserResp user;
  private SessionResp lastSession;

  public AuthResp(String token, TokenType tokenType) {
    this.token = token;
    this.tokenType = tokenType;
  }
}
