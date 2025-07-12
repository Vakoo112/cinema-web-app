package com.example.payment_service.v1.kafka;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage<T extends KafkaMessageData> {

  private KafkaMessageEvent event;
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "className")
  private T data;
}
