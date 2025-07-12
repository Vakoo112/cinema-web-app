package com.example.ticket_um.v1.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "m1_user_sessions")
public class UserSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "user_id")
  private Users user;
  private String ipAddress;
  @Column(name = "token", length = 4000)
  private String token;
  private String lastActPath;
  private LocalDateTime lastActDate;
  @CreatedDate
  @Column(name = "create_date")
  private LocalDateTime createdDate;
  @LastModifiedDate
  @Column(name = "modify_date")
  private LocalDateTime modifiedDate;

  public UserSession(Users user, String ipAddress, String token) {
    this(user, ipAddress, null, token);
  }

  public UserSession(Users user, String ipAddress, Integer otp) {
    this(user, ipAddress, otp, null);
  }

  public UserSession(Users user, String ipAddress, Integer otp, String token) {
    this.user = user;
    this.ipAddress = ipAddress;
    this.token = token;
    this.lastActDate = LocalDateTime.now();
  }
}
