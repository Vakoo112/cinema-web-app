package com.example.ticket_utils.v1.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversalDto implements UniversalDtoI {

  @JsonInclude(NON_NULL)
  private Long id;
  @JsonInclude(NON_NULL)
  private String name;
  @JsonInclude(NON_NULL)
  private Long parentId;
  @JsonInclude(NON_NULL)
  private String prefix;

  public UniversalDto(UniversalDtoI src) {
    this.id = src.getId();
    this.name = src.getName();
    this.parentId = src.getParentId();
    this.prefix = src.getPrefix();
  }
}
