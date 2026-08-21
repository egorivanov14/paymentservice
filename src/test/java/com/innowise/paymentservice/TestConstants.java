package com.innowise.paymentservice;

import com.innowise.paymentservice.dto.PaymentStatus;

import java.time.LocalDateTime;

public class TestConstants {
  public static final String MONGO_DOCKER_IMAGE_NAME = "mongo:8";
  public static final String MONGO_URI_PATH="spring.mongodb.uri";
  public static final String LIQUIBASE_MONGO_URL_PATH = "spring.liquibase.url";
  public static final Long ORDER_ID = 1L;
  public static final Long USER_ID = 1L;
  public static final Long PAYMENT_AMOUNT = 100L;
  public static final LocalDateTime TIMESTAMP = LocalDateTime.now();
  public static final PaymentStatus PAYMENT_STATUS = PaymentStatus.SUCCESS;
  public static final String MONGO_COLLECTION_NAME = "payments";
}