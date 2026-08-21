package com.innowise.paymentservice.client.dto;

public record GetRandomIntNumberRequest(
        String jsonrpc,
        String method,
        GetRandomIntNumberParams params,
        int id
) {
}