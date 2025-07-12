package com.example.payment_service.v1.controller.external;

import com.example.payment_service.v1.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("external/v1/webhook")
public class StripeWebhookController {

  private final PaymentService paymentService;

  @PostMapping("stripe")
  public ResponseEntity<String> handleStripeWebhookAsync(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
    try {
      paymentService.handleStripeWebhookAsynch(payload, sigHeader);
      return ResponseEntity.ok("Webhook received");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body("Webhook error: " + e.getMessage());
    }
  }
}

