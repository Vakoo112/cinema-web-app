package com.example.ticket_utils.v1.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ExtendableDate extends ComparableEntity {

  @CreatedDate
  @Column(name = "create_date")
  private LocalDateTime createdDate;
  @LastModifiedDate
  @Column(name = "modify_date")
  private LocalDateTime modifiedDate;
}
