package com.example.ticket_um.v1.domain.model;

import com.example.ticket_um.v1.domain.model.dc.PermType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "permissions_dc")
public class Permission  implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "permission_id")
  private Long id;
  @Column(name = "permission_name")
  private String name;
  @Column(name = "is_active")
  private Boolean active = true;
  @ManyToOne
  @JoinColumn(name = "gl_um_permission_type_id")
  private PermType type;
  @Column(name = "permission_description")
  private String permDescription;
  private String keyword;

  @Override
  public String getAuthority() {
    return keyword;
  }
}
