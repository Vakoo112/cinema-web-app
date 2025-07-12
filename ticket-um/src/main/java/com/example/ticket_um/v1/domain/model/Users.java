package com.example.ticket_um.v1.domain.model;

import com.example.ticket_um.v1.domain.model.enums.ContactType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@Table(name = "m1_managment_users")
public class Users implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @SequenceGenerator(name = "user_id_seq", sequenceName = "USER_ID_SEQ", allocationSize = 1)
  @Column(name = "user_id")
  private Long Id;
  @Column(name = "create_user_id")
  private Long createUserId;
  @Column(name = "create_date")
  private LocalDateTime createDate;
  @Column(name = "modify_user_id")
  private Long modifyUserId;
  @Column(name = "modify_date")
  private LocalDateTime modifyDate;
  @Column(name = "GROUP_ID")
  private Long groupId;
  private String username;
  @Column(name = "user_password")
  private String password;
  private String firstName;
  private String lastName;
  @Column(name = "is_active")
  private boolean active = true;
  private String email;
  @Column(name = "id_number")
  private String idnumber;
  private LocalDateTime pwdChngDate;
  private Long prntUserId;
  private LocalDateTime pwdNextChngDate;
  private Integer authFailAttempts;
  @Column(name = "LOCK_DATE")
  private LocalDateTime lockDate;
  @Column(name = "UNLOCK_DATE")
  private LocalDateTime unlockDate;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
  private List<UserContact> contacts = new ArrayList<>();

  public boolean addRole(Role role) {
    return this.roles.add(role);
  }

  public boolean delRole(Role role) {
    return this.roles.remove(role);
  }

  public int incAuthFailAttempts() {
    if (this.authFailAttempts >= 10) {
      this.authFailAttempts = 0;
    }
    return ++this.authFailAttempts;
  }

  public boolean addContact(ContactType type, String contact) {
    var userContact = new UserContact(this, type, contact);
    return this.contacts.add(userContact);
  }

  @Override
  public Set<Permission> getAuthorities() {
    return roles.stream()
        .flatMap(r -> r.getPermissions().stream())
        .collect(Collectors.toSet());
  }

  @Override
  public String getPassword() {
    return this.password;
  }
}