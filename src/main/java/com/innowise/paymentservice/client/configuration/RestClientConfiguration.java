package com.innowise.paymentservice.client.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
  @Bean
  public RestClient randomRestClient(@Value("${client.url}") String baseUrl) {
    return RestClient.builder().baseUrl(baseUrl).build();
  }
}