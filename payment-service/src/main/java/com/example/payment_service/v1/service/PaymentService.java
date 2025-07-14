package com.example.payment_service.v1.service;

import com.example.payment_service.v1.domain.Payment;
import com.example.payment_service.v1.domain.dto.CreatePaymentReq;
import com.example.payment_service.v1.domain.enums.PaymentStatus;
import com.example.payment_service.v1.kafka.KafkaMessageProduce;
import com.example.payment_service.v1.kafka.PaymentData;
import com.example.payment_service.v1.repository.PaymentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepo paymentRepo;
  private final ObjectMapper objectMapper;
  @Value("${stripe.webhook.secret}")
  private String stripeWebhookSecret;
  @Value("${stripe.secret.key}")
  private String stripeApiKey;
  private final KafkaMessageProduce kafkaMessageProduce;

  public Session createCheckoutSession(CreatePaymentReq req) throws StripeException {
    Stripe.apiKey = stripeApiKey;
    
    // save payment in DB first
    var payment = new Payment();
    payment.setSeatBookingId(req.getSeatBookingId());
    payment.setAmount(req.getAmount() * 1000);
    payment.setCurrency(req.getCurrency());
    payment.setStatus(PaymentStatus.PENDING); // default
    payment = paymentRepo.save(payment);

    // Create Stripe Checkout session
    var params = SessionCreateParams.builder()
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl("https://yourdomain.com/success?session_id={CHECKOUT_SESSION_ID}")
        .setCancelUrl("https://yourdomain.com/cancel")
        .addLineItem(SessionCreateParams.LineItem.builder()
            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(req.getCurrency())
                .setUnitAmount((long) req.getAmount()) // already in cents
                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName("Seat Booking #" + req.getSeatBookingId())
                    .build())
                .build())
            .setQuantity(1L)
            .build())
        .putMetadata("paymentId", String.valueOf(payment.getId()))
        .build();

    return Session.create(params);
  }


  @Async
  public void handleStripeWebhookAsynch(String payload, String sigHeader) throws Exception {
    // Validate and parse event
    Event event = Webhook.constructEvent(payload, sigHeader, stripeWebhookSecret);
    var eventType = event.getType();

    if ("checkout.session.completed".equals(eventType)) {
      // parse raw JSON to reliably get paymentIntent and metadata.paymentId
      JsonNode root = objectMapper.readTree(payload);
      JsonNode dataObject = root.path("data").path("object");

      String paymentIntentId = dataObject.path("payment_intent").asText(null);
      String paymentIdStr = dataObject.path("metadata").path("paymentId").asText(null);

      if (paymentIntentId == null || paymentIdStr == null) {
        throw new IllegalArgumentException("Missing paymentIntentId or paymentId in webhook payload");
      }

      Long paymentId = Long.parseLong(paymentIdStr);

      var payment = paymentRepo.findById(paymentId).orElseThrow();

      payment.setStripePaymentIntentId(paymentIntentId);
      payment.setStatus(PaymentStatus.SUCCESS);
      paymentRepo.saveAndFlush(payment);

      var paymentData = new PaymentData();
      paymentData.setPaymentStatus(PaymentStatus.SUCCESS);
      paymentData.setSeatBookingId(payment.getSeatBookingId());
      paymentData.setUserId(payment.getCreatedBy());
      paymentData.setPaymentId(paymentId);

      System.out.println("Webhook received. Sending Kafka message...");

      kafkaMessageProduce.sendPaymentMessage(paymentData)
          .exceptionally(ex -> {
            System.err.println(" Kafka send failed: " + ex.getMessage());
            ex.printStackTrace();
            return null;
          });

    } else if ("payment_intent.payment_failed".equals(eventType)) {
      PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow();
      String paymentIdStr = intent.getMetadata().get("paymentId");
      Long paymentId = Long.parseLong(paymentIdStr);

      var payment = paymentRepo.findById(paymentId).orElseThrow();
      payment.setStatus(PaymentStatus.FAILED);
      paymentRepo.saveAndFlush(payment);

      var paymentData = new PaymentData();
      paymentData.setPaymentStatus(PaymentStatus.FAILED);
      paymentData.setSeatBookingId(payment.getSeatBookingId());
      paymentData.setPaymentId(paymentId);

      kafkaMessageProduce.sendPaymentMessage(paymentData)
          .exceptionally(ex -> {
            System.err.println(" Kafka send failed: " + ex.getMessage());
            ex.printStackTrace();
            return null;
          });
    }
  }
}
