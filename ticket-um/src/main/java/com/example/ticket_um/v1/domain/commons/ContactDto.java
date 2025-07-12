package com.example.ticket_um.v1.domain.commons;

import com.example.ticket_um.v1.domain.model.enums.ContactType;
import lombok.Data;

@Data
public class ContactDto {

  private Long id;
  private ContactType type;
  private String contact;

  public String getContact() {
    return contact.strip();
  }
}

