package com.innowise.paymentservice.exception;

public class ExternalServerException extends RuntimeException {
  public ExternalServerException(String message) {
        super(message);
    }
}