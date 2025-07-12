package com.example.ticket_um.v1.domain.dto;

import com.example.ticket_um.v1.domain.commons.ContactDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.Set;

@Data
public class UserReq {

  @NotBlank
  @Pattern(regexp = "^[A-Z0-9_]+$")
  private String username;
  @NotBlank
  private String firstName;
  private String email;
  @NotBlank
  private String lastName;
  private String idnumber;
  private Long prntUserId;
  private Set<ContactDto> contacts;

  public String getUsername() {
    return this.username.strip().toUpperCase();
  }

  public String getFirstName() {
    return firstName.strip();
  }

  public String getLastName() {
    return lastName.strip();
  }

  public String getIdnumber() {
    return idnumber != null ? idnumber.strip() : null;
  }
}