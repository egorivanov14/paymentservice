package com.innowise.paymentservice.configuration;

public class Constants {
  public static final String USER_ID_MONGO_KEY = "user_id";
  public static final String PAYMENT_AMOUNT_MONGO_KEY = "payment_amount";
  public static final String TOTAL_SUM_VARIABLE = "totalSum";
  public static final String PAYMENT_MONGO_COLLECTION_NAME = "payments";
  public static final String TIMESTAMP_MONGO_KEY = "timestamp";
  public static final int RANDOM_MIN_NUMBER = 1;
  public static final int RANDOM_MAX_NUMBER = 100;
  public static final String RANDOM_CLIENT_API_KEY = "d2b26f43-4a11-45b2-813e-0bdf59dda3d0";
  public static final int RANDOM_NUMBERS_QUANTITY = 1;
  public static final String JSONRPC = "jsonrpc";
  public static final String RANDOM_CLIENT_METHOD = "generateIntegers";
  public static final int RANDOM_CLIENT_REQUEST_ID = 976824;
  public static final String KAFKA_PAYMENTS_TOPIC_NAME = "payment-events";
  public static final int KAFKA_PAYMENTS_TOPIC_PARTITIONS_NUMBER = 1;
}