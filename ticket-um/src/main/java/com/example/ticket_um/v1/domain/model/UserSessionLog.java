package com.example.ticket_um.v1.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "m1_user_sessions_log")
public class UserSessionLog {

  @Id
  @Column(name = "log_id")
  private Long logId;
  private LocalDateTime logDate;
  private Long id;
  @Column(name = "create_date")
  private LocalDateTime createdDate;
  @Column(name = "modify_date")
  private LocalDateTime modifiedDate;
  private Long userId;
  private String ipAddress;
  private String otp;
  private String token;
  private String lastActPath;
  private LocalDateTime lastActDate;
}
