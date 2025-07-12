package com.example.ticket_um.v1.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "m1_um_groups")
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
  @SequenceGenerator(name = "group_seq", sequenceName = "t0014_pk_gen", allocationSize = 1)
  @Column(name = "um_group_id")
  private Long id;
  @Column(name = "is_active")
  private boolean active = true;
  @Column(name = "group_name")
  private String name;
  @Column(name = "group_description")
  private String description;
  @Column(name = "um_group_char_id")
  private String keyword;
}
