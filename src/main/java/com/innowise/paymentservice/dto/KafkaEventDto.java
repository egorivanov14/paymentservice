package com.innowise.paymentservice.dto;

public record KafkaEventDto(
        Long orderId,
        PaymentStatus status
) {
}