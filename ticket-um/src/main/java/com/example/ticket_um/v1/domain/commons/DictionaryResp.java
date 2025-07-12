package com.example.ticket_um.v1.domain.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class DictionaryResp {

  private Long id;
  private String name;
  @JsonInclude(NON_NULL)
  private String description;
  @JsonInclude(NON_NULL)
  private Long parentId;
  private boolean active;
}

