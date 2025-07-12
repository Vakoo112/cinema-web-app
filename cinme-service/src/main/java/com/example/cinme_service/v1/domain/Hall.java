package com.example.cinme_service.v1.domain;

import com.example.ticket_utils.v1.domain.model.Extendable;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m2_halls")
public class Hall extends Extendable {

  @Id
  @GeneratedValue
  @Column(name = "hall_id")
  private Long id;
  private String name;
  private String type;
  private String location;
  private Boolean deleted = false;
  private Integer capacity;
}