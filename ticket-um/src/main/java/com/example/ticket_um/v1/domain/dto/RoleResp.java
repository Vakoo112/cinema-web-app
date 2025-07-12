package com.example.ticket_um.v1.domain.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RoleResp {

  private Long id;
  private Boolean active;
  private String name;
  private String description;
  private Set<PermResp> permissions;
}
