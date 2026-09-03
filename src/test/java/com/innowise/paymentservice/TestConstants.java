package com.innowise.paymentservice;

import com.innowise.paymentservice.dto.PaymentStatus;

import java.time.LocalDateTime;

public class TestConstants {
  public static final String MONGO_DOCKER_IMAGE_NAME = "mongo:8";
  public static final Long ORDER_ID = 1L;
  public static final Long USER_ID = 1L;
  public static final Long PAYMENT_AMOUNT = 100L;
  public static final String RANDOM_NUMBER_CLIENT_STUB_JSON = """
          {
            "request": {
              "method": "POST",
              "url": "/"
            },
            "response": {
              "status": 200,
              "headers": {
                "Content-Type": "application/json"
              },
              "jsonBody": {
                "result": {
                  "random": {
                    "data": [2]
                  }
                }
              }
            }
          }
          """;
}