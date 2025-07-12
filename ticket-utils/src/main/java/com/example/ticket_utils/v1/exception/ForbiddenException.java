package com.example.ticket_utils.v1.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

  private final Error keyword;

  public ForbiddenException(Error keyword) {
    super(keyword.getMessage());
    this.keyword = keyword;
  }

  public ForbiddenException(Error keyword, String message) {
    super(message);
    this.keyword = keyword;
  }
}
