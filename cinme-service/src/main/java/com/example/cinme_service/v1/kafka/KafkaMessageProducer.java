package com.example.cinme_service.v1.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

import static com.example.cinme_service.v1.kafka.KafkaMessageEvent.NOTIFICATION;
import static com.example.ticket_utils.v1.config.AuthorizationUtil.getAuthUser;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducer {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Value("${notif-api-topic}")
  private String notifTopic;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public CompletableFuture<SendResult<String, String>> sendMessage(String topic, KafkaMessage message) {
    try {
      var pr = new ProducerRecord<String, String>(topic, objectMapper.writeValueAsString(message));

      var user = getAuthUser();  // 💡 Might be null
      if (user != null && user.getId() != null) {
        pr.headers().add("X-User-ID", user.getId().toString().getBytes());
      } else {
        System.out.println("No authenticated user — skipping X-User-ID header.");
      }

      return kafkaTemplate.send(pr);

    } catch (JsonProcessingException ex) {
      throw new IllegalStateException(ex);
    }
  }

  public CompletableFuture<SendResult<String, String>> sendNotificationMessage(NotificationData data) {
    return sendMessage(notifTopic, new KafkaMessage(NOTIFICATION, data))
        .whenComplete((result, ex) -> {
          if (ex != null) {
           System.out.println("Failed to send notification message");
          } else {
            System.out.println("Notification Kafka message sent to topic ");
          }
        });
  }
}
