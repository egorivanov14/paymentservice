package com.innowise.paymentservice.exception;

import com.innowise.paymentservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
    int status = HttpStatus.NOT_FOUND.value();
    String message = e.getMessage();
    LocalDateTime timestamp = LocalDateTime.now();
    ErrorResponse errorResponse = new ErrorResponse(status, message, timestamp);
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ExternalServerException.class)
  public ResponseEntity<ErrorResponse> handleExternalServerException(ExternalServerException e) {
    int status = HttpStatus.BAD_GATEWAY.value();
    String message = e.getMessage();
    LocalDateTime timestamp = LocalDateTime.now();
    ErrorResponse errorResponse = new ErrorResponse(status, message, timestamp);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
  }
}