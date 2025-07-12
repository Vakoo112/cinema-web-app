package com.example.ticket_um.v1.domain.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "createdDate", "ipAddress"})
public interface SessionResp {

  Long getId();

  LocalDateTime getCreatedDate();

  String getIpAddress();
}

