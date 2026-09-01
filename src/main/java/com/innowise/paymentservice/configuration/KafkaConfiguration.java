package com.innowise.paymentservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.innowise.paymentservice.configuration.Constants.KAFKA_PAYMENTS_TOPIC_NAME;
import static com.innowise.paymentservice.configuration.Constants.KAFKA_PAYMENTS_TOPIC_PARTITIONS_NUMBER;

@Configuration
public class KafkaConfiguration {
  @Bean
  public NewTopic newTopic() {
    return TopicBuilder
            .name(KAFKA_PAYMENTS_TOPIC_NAME)
            .partitions(KAFKA_PAYMENTS_TOPIC_PARTITIONS_NUMBER)
            .build();
  }
}