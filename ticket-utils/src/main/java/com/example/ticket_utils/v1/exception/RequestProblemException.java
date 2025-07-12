package com.example.ticket_utils.v1.exception;

import lombok.Getter;

@Getter
public class RequestProblemException extends RuntimeException {

  private final Error keyword;

  public RequestProblemException(Error keyword) {
    super(keyword.getMessage());
    this.keyword = keyword;
  }

  public RequestProblemException(Error keyword, String message) {
    super(message);
    this.keyword = keyword;
  }
}
