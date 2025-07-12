package com.example.payment_service.v1.config;

import com.example.payment_service.v1.client.UserManagmentApiClient;
import com.example.ticket_utils.v1.config.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.example.ticket_utils")
@EnableAsync
@EnableScheduling
public class AppConfig {

  @Bean
  public UserManagmentApiClient userManagmentApiClient(@Value("${um.api.uri}") String umApiUri) {
    return Util.startApiClient(UserManagmentApiClient.class, umApiUri);
  }
}
