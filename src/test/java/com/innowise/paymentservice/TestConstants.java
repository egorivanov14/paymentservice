package com.innowise.paymentservice;

import com.innowise.paymentservice.client.dto.GetRandomIntResponse;
import com.innowise.paymentservice.client.dto.GetRandomIntResult;
import com.innowise.paymentservice.client.dto.GetRandomIntResultRandom;
import com.innowise.paymentservice.dto.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class TestConstants {
  public static final String MONGO_DOCKER_IMAGE_NAME = "mongo:8";
  public static final Long ORDER_ID = 1L;
  public static final Long USER_ID = 1L;
  public static final GetRandomIntResponse RANDOM_INT_RESPONSE = new GetRandomIntResponse(new GetRandomIntResult(new GetRandomIntResultRandom(List.of(2))));
  public static final Long PAYMENT_AMOUNT = 100L;
  public static final String ID = "id";
  public static final String PAYMENT_ID = "paymentId";
  public static final PaymentStatus SUCCESS = PaymentStatus.SUCCESS;
  public static final PaymentStatus FAILED = PaymentStatus.FAILED;
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