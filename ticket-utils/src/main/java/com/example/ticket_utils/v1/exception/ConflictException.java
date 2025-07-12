package com.example.ticket_utils.v1.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

  private final Error keyword;

  public ConflictException(Error keyword) {
    super(keyword.getMessage());
    this.keyword = keyword;
  }

  public ConflictException(Error keyword, String message) {
    super(message);
    this.keyword = keyword;
  }
}
