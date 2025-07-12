package com.example.ticket_utils.v1.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Resp<T> {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Error> errors;

  public Resp(T t) {
    this.data = t;
  }

  public Resp(List<Error> errors) {
    this.errors = errors;
  }

  public Resp(Error error) {
    this(List.of(error));
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Error {

    private String keyword;
    private String message;
  }
}
