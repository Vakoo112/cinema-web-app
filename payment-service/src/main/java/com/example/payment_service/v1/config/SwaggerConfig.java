package com.example.payment_service.v1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi externalApi() {
    return GroupedOpenApi
        .builder()
        .group("1.external-api")
        .packagesToScan("com.example.payment_service.v1.controller.external")
        .pathsToMatch("/external/**")
        .addOperationCustomizer(operationCustomizerExt())
        .build();
  }

  @Bean
  public GroupedOpenApi internalApi() {
    return GroupedOpenApi
        .builder()
        .group("2.internal-api")
        .packagesToScan("com.example.payment_service.internal")
        .pathsToMatch("/internal/**")
        .addOperationCustomizer(operationCustomizerIntl())
        .build();
  }

  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI()
        .info(new Info()
            .title("Payment Api")
            .description("Rest Api For Payment")
            .version("1.0"));
  }

  public OperationCustomizer operationCustomizerExt() {
    return (operation, handlerMethod) -> operation
        .addParametersItem(new Parameter()
            .in("header")
            .name("X-User-ID")
            .required(true)
            .schema(new IntegerSchema().format("int64"))
            .description("Authenticated User"));
  }

  public OperationCustomizer operationCustomizerIntl() {
    return (operation, handlerMethod) -> operation
        .addParametersItem(new Parameter()
            .in("header")
            .name("X-User-ID")
            .required(true)
            .schema(new IntegerSchema().format("int64"))
            .description("Authenticated User"));
  }
}
