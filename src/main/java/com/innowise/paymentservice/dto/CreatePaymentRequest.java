package com.innowise.paymentservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreatePaymentRequest(
        @NotNull
        Long orderId,
        @NotNull
        Long userId,
        @NotNull
        @Min(0L)
        Long paymentAmount
) {
}