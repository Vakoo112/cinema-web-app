package com.example.ticket_utils.v1.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

  private final Error keyword;

  public UnauthorizedException(Error keyword) {
    super(keyword.getMessage());
    this.keyword = keyword;
  }

  public UnauthorizedException(Error keyword, String message) {
    super(message);
    this.keyword = keyword;
  }
}
