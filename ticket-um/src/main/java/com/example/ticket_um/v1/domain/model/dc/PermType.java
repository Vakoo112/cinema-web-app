package com.example.ticket_um.v1.domain.model.dc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dc_permission_types")
public class PermType {

  @Id
  @Column(name = "gl_um_permission_type_id")
  private Long id;
  @Column(name = "permission_type_name")
  private String name;
  private String description;
}
