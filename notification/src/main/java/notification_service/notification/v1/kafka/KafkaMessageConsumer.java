package notification_service.notification.v1.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification_service.notification.v1.client.UserManagmentApiClient;
import notification_service.notification.v1.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import static notification_service.notification.v1.kafka.KafkaMessageEvent.NOTIFICATION;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaMessageConsumer {

  private final UserManagmentApiClient userManagmentApiClient;
  private final NotificationService notifService;

  @KafkaListener(
      topics = "${kafka.topics.notif}",
      groupId = "${spring.kafka.consumer.group-id}"
  )
  public void consume(@Payload KafkaMessage msg, @Header(value = "X-User-ID", required = false) String authUserId) {
    if (authUserId != null) {
      try {
        setAuthentication(Long.parseLong(authUserId));
      } catch (NumberFormatException e) {
        log.warn("Invalid X-User-ID header value: {}", authUserId);
        SecurityContextHolder.clearContext();
      }
    } else {
      log.warn("Kafka message received without X-User-ID header");
      SecurityContextHolder.clearContext();
    }

    if (msg.getEvent() == NOTIFICATION) {
      NotificationData data = (NotificationData) msg.getData();
      notifService.add(data);
    } else {
      log.error("Unknown Kafka message event type: {}", msg.getEvent());
    }
  }

  private void setAuthentication(long authUserId) {
    var authUser = userManagmentApiClient.getUserDtl(authUserId);
    var authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}