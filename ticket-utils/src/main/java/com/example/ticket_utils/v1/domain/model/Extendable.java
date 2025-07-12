package com.example.ticket_utils.v1.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Extendable extends ComparableEntity {

  @CreatedBy
  @Column(name = "create_user_id")
  private Long createdBy;
  @CreatedDate
  @Column(name = "create_date")
  private LocalDateTime createdDate;
  @LastModifiedBy
  @Column(name = "modify_user_id")
  private Long modifiedBy;
  @LastModifiedDate
  @Column(name = "modify_date")
  private LocalDateTime modifiedDate;
}

