package com.example.cinme_service.v1.kafka;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("NotificationData")
public class NotificationData extends KafkaMessageData {

  private Long userId;
  private Long movieShowId;
  private String uniqueCode;
  private String notifTypeKeyword;
  private Map<String, String> parameters = new HashMap<>();

  public NotificationData(Long userId, Long movieShowId, String uniqueCode, String notifTypeKeyword) {
    this(userId, movieShowId, uniqueCode, notifTypeKeyword, Map.of());
  }
}
