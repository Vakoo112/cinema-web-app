package com.example.payment_service.v1.controller.external;

import com.example.payment_service.v1.domain.dto.CreatePaymentReq;
import com.example.payment_service.v1.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("external/v1/payments")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("checkout-session")
  public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody CreatePaymentReq req) throws StripeException {
    Session session = paymentService.createCheckoutSession(req);
    Map<String, String> response = new HashMap<>();
    response.put("checkoutUrl", session.getUrl());
    return ResponseEntity.ok(response);
  }
}