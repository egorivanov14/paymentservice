package com.innowise.paymentservice.client.dto;

public record GetRandomIntNumberParams(
        String apiKey,
        int n,
        int min,
        int max
) {
}