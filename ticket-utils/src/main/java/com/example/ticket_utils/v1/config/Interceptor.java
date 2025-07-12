package com.example.ticket_utils.v1.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;
import static com.example.ticket_utils.v1.config.AuthorizationUtil.getAuthUser;

public class Interceptor implements ClientHttpRequestInterceptor {
  public Interceptor() {
  }

  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    String authUserId = getAuthUser() != null ? getAuthUser().getId().toString() : "11";
    request.getHeaders().add("X-User-ID", authUserId);
    return execution.execute(request, body);
  }
}