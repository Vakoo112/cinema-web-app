package com.example.payment_service.v1.client;

import com.example.ticket_utils.v1.domain.dto.UserDtl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("internal/v1")
public interface UserManagmentApiClient {

  @GetExchange("users/{id}")
  UserDtl getUserDtl(@PathVariable long id);
}
