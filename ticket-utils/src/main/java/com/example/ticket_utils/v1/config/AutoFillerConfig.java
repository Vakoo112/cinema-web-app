package com.example.ticket_utils.v1.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AutoFillerConfig {

  @Bean
  public AuditorAware<Long> auditorProvider() {
    return () -> {
      var user = AuthorizationUtil.getAuthUser();
      if (user == null) {
        return Optional.of(11l);
      }
      return Optional.of(user.getId());
    };
  }

}
