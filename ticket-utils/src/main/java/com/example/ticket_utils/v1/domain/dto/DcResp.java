package com.example.ticket_utils.v1.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class DcResp {

  private Long id;
  private String name;
  @JsonInclude(NON_NULL)
  private String description;
  @JsonInclude(NON_NULL)
  private Long parentId;
  private boolean active;
}
