package com.example.payment_service.v1.kafka;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "className")
@JsonSubTypes({@JsonSubTypes.Type(value = PaymentData.class, name = "PaymentData")})
public abstract class KafkaMessageData {

}
