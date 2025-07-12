package notification_service.notification.v1.domain;

import com.example.ticket_utils.v1.domain.model.Dc;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dc_types")
public class Type extends Dc {

  @Id
  @Column(name = "type_id")
  private Long id;
  private String name;
  @Column(name = "is_active")
  private boolean active = true;
  String template;
  String keyword;
}