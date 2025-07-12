package notification_service.notification.v1.domain.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class NotificationSearch {

  private Long sysModuleId;
  private String uniqueId;
  private LocalDate createdDateFrom;
  private LocalDate createdDateTo;
  private LocalDate readDateFrom;
  private LocalDate readDateTo;
}
