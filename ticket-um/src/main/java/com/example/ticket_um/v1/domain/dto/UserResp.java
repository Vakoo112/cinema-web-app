package com.example.ticket_um.v1.domain.dto;

import com.example.ticket_um.v1.domain.commons.ContactDto;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UserResp {

  private Long id;
  private Boolean active;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String idnumber;
  private LocalDateTime pwdChngDate;
  private LocalDateTime pwdNextChngDate;
  private List<ContactDto> contacts;
  private Set<RoleResp> roles;
  private Set<PermResp> authorities;
}
