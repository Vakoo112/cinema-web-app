package com.example.ticket_um.v1.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Long id;
  @Column(name = "is_active")
  private boolean active = true;
  @Column(name = "role_name")
  private String name;
  @Column(name = "role_description")
  private String description;
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "m1_group_id")
  private Group group;
  @ManyToMany
  @JoinTable(
      name = "role_permissions",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id")
  )
  private Set<Permission> permissions = new HashSet<>();

  public boolean addPerm(Permission perm) {
    return this.permissions.add(perm);
  }

  public boolean delPerm(Permission perm) {
    return this.permissions.remove(perm);
  }
}