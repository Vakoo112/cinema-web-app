package com.example.ticket_um.v1.domain.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserSearchReq {

  private Boolean active;
  private String username;
  private String firstName;
  private String lastName;
  private String idNumber;
  private String email;
  private String phone;
  private List<Long> permTypeIds;
  private List<Long> permissionIds;
}
