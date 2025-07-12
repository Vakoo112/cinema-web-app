package com.example.payment_service.v1.config.security;

import com.example.ticket_utils.v1.config.AbstractAuthFilter;
import com.example.ticket_utils.v1.config.BaseSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
@Configuration
public class SecurityConfig extends BaseSecurityConfig {

  public SecurityConfig(AbstractAuthFilter authFilter) {
    super(authFilter);
  }

  @Override
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("external/v1/webhook/**").permitAll()
    );
    return super.securityFilterChain(http);
  }
}
