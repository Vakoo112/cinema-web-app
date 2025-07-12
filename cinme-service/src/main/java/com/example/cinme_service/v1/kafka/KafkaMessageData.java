package com.example.cinme_service.v1.kafka;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "className")
@JsonSubTypes({@JsonSubTypes.Type(value = PaymentData.class, name = "PaymentData"),
               @JsonSubTypes.Type(value = NotificationData.class, name = "NotificationData")})
public abstract class KafkaMessageData {

}

