package com.example.ticket_utils.v1.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageReq<T> extends Req<T> {

  @Valid
  private PageSizeReq page;
  @Valid
  private List<SortReq> sort;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PageSizeReq {

    @NotNull
    private Integer number;
    @NotNull
    private Integer size;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SortReq {

    @NotNull
    private String property;
    @NotNull
    private Sort.Direction direction;
  }
}