package notification_service.notification.v1.kafka;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
@JsonTypeName("NotificationData")
public class NotificationData extends KafkaMessageData {

  private Long userId;
  private Long movieShowId;
  private String uniqueCode;
  private String notifTypeKeyword;
  private Map<String, String> parameters = new HashMap<>();
}
