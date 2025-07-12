package notification_service.notification.v1.domain;

import com.example.ticket_utils.v1.domain.model.Extendable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "m4_notifications")
public class Notification extends Extendable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notif_seq")
  @Column(name = "notification_id")
  private Long id;
  @Column(name = "user_id")
  private Long userId;
  @Column(name = "is_read")
  private boolean read = false;
  private Long movieShowId;
  private String uniqueCode;
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "type_id")
  private Type notifType;
  @Column(name = "event_text")
  private String notifText;
}