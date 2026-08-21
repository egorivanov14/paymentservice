package com.innowise.paymentservice.dto;

import java.time.LocalDateTime;

public record PaymentResponse(
        String id,
        Long orderId,
        Long userId,
        PaymentStatus status,
        LocalDateTime timestamp,
        Long paymentAmount
) {
}