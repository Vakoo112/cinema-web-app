package com.example.ticket_um.v1.domain.model;

import com.example.ticket_um.v1.domain.model.enums.ContactType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "m1_user_contacts")
public class UserContact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id")
  private Users user;
  @Enumerated(EnumType.STRING)
  private ContactType type;
  private String contact;

  public UserContact(Users user, ContactType type, String contact) {
    this.user = user;
    this.type = type;
    this.contact = contact;
  }
}

