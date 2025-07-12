package com.example.ticket_utils.v1.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

  private final Error keyword;

  public ResourceNotFoundException(Error keyword) {
    super(keyword.getMessage());
    this.keyword = keyword;
  }

  public ResourceNotFoundException(Error keyword, String message) {
    super(message);
    this.keyword = keyword;
  }
}
