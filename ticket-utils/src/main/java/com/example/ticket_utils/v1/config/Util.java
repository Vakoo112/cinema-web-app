package com.example.ticket_utils.v1.config;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class Util {
  private Util() {
  }

  public static <T> T startApiClient(Class<T> apiClientClass, String apiUri) {
    RestClient restClient = RestClient.builder().baseUrl(apiUri).requestInterceptor(new Interceptor()).build();
    HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    return proxyFactory.createClient(apiClientClass);
  }
}