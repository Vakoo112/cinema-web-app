package com.example.cinme_service.v1.kafka;

import com.example.cinme_service.v1.client.UserManagmentApiClient;
import com.example.cinme_service.v1.service.SeatBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.example.cinme_service.v1.kafka.KafkaMessageEvent.PAYMENT;

@Component
@RequiredArgsConstructor
public class KafkaMessageConsumer {

  private final SeatBookingService seatBookingService;
  private final UserManagmentApiClient userManagmentApiClient;

  @KafkaListener(
      topics = "${cinema.api.topic}",
      groupId = "${spring.kafka.consumer.group-id}"
  )
  public void consume(
      @Payload KafkaMessage msg,
      @Header(value = "X-User-ID", required = false) String authUserId) {
    if (authUserId != null) {
      setAuthentication(Long.parseLong(authUserId));
    } else if (msg.getEvent() == PAYMENT) {
      var paymentData = (PaymentData) msg.getData();
      if (paymentData.getUserId() != null) {
        //Restore authentication from payload
        setAuthentication(paymentData.getUserId());
        System.out.println("Auth restored from PaymentData.userId: {}");
      } else {
        System.out.println("No auth header or userId in payment data, cannot authenticate");
      }
    }
    switch (msg.getEvent()) {
      case PAYMENT -> {
        var paymentData = (PaymentData) msg.getData();
        seatBookingService.updatePaymentStatusDetails(paymentData);
      }
    }
  }


  private void setAuthentication(long authUserId) {
    var authUser = userManagmentApiClient.getUserDtl(authUserId);
    var authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
