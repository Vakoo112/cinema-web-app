package com.example.ticket_um.v1.domain.dto;

import com.example.ticket_um.v1.domain.commons.ContactDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;

@Data
public class EditUserReq {

  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  private String idnumber;
  private Long prntUserId;
  private Set<ContactDto> contacts;

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
