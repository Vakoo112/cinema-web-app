package com.example.ticket_utils.v1.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageResp<T> extends Resp<T> {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private PageSizeResp page;

  public PageResp(T t, PageSizeResp page) {
    super(t);
    this.page = page;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PageSizeResp {

    private Long totalRecords;
    private Integer totalPages;
    private Integer size;
    private Integer number;
  }
}