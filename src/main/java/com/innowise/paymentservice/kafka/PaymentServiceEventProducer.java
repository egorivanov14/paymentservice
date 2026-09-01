package com.innowise.paymentservice.kafka;

import com.innowise.paymentservice.dto.KafkaEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.innowise.paymentservice.configuration.Constants.KAFKA_PAYMENTS_TOPIC_NAME;

@Component
public class PaymentServiceEventProducer {

  private final KafkaTemplate<String, KafkaEventDto> kafkaTemplate;

  public PaymentServiceEventProducer(KafkaTemplate<String, KafkaEventDto> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendPaymentEvent(KafkaEventDto kafkaEventDto) {
    kafkaTemplate.send(KAFKA_PAYMENTS_TOPIC_NAME, kafkaEventDto);
  }
}