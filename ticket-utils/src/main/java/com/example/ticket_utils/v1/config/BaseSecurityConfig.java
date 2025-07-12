package com.example.ticket_utils.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class BaseSecurityConfig {
  private final AbstractAuthFilter authFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults());
    http.csrf((csrf) -> {
      csrf.disable();
    });
    http.sessionManagement((session) -> {
      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });
    http.exceptionHandling((exceptionHandling) -> {
      exceptionHandling.authenticationEntryPoint((req, resp, ex) -> {
        resp.sendError(401, ex.getMessage());
      });
    });
    http.authorizeHttpRequests((auth) -> {
      ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl) ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl) ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl) ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl) auth.requestMatchers(new String[]{"/"})).permitAll().requestMatchers(new String[]{"/swagger-ui/**"})).permitAll().requestMatchers(new String[]{"/v3/api-docs/**"})).permitAll().anyRequest()).authenticated();
    });
    http.addFilterBefore(this.authFilter, UsernamePasswordAuthenticationFilter.class);
    return (SecurityFilterChain) http.build();
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOriginPattern("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  public BaseSecurityConfig(final AbstractAuthFilter authFilter) {
    this.authFilter = authFilter;
  }
}
