package com.example.cinme_service.v1.config;

import com.example.cinme_service.v1.client.OmdbApiClient;
import com.example.cinme_service.v1.client.UserManagmentApiClient;
import com.example.ticket_utils.v1.config.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@ComponentScan("com.example.ticket_utils")
@EnableAsync
@EnableScheduling
public class AppConfig {

  @Bean
  public OmdbApiClient omdbApiClient(@Value("${omdb.api.url}") String omdbApiUrl) {
    RestClient restClient = RestClient.builder()
        .baseUrl(omdbApiUrl)
        .build();

    HttpServiceProxyFactory factory = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build();

    return factory.createClient(OmdbApiClient.class);
  }

  @Bean
  public UserManagmentApiClient userManagmentApiClient(@Value("${um.api.uri}") String umApiUri) {
    return Util.startApiClient(UserManagmentApiClient.class, umApiUri);
  }
}