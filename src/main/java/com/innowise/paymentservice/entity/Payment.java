package com.innowise.paymentservice.entity;

import com.innowise.paymentservice.dto.PaymentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "payments")
public class Payment {
  @Id
  private Long id;

  @Field(name = "order_id")
  private Long orderId;

  @Field(name = "user_id")
  private Long userId;

  private PaymentStatus status;

  private LocalDateTime timestamp;

  @Field(name = "payment_amount")
  private Long paymentAmount;

  public Payment() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public PaymentStatus getStatus() {
    return status;
  }

  public void setStatus(PaymentStatus status) {
    this.status = status;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Long getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(Long paymentAmount) {
    this.paymentAmount = paymentAmount;
  }
}