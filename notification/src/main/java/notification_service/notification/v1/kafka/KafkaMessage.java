package notification_service.notification.v1.kafka;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
public class KafkaMessage<T extends KafkaMessageData> {

  private KafkaMessageEvent event;
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "className")
  private T data;

}
