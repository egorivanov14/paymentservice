package com.innowise.paymentservice.service;

import com.innowise.paymentservice.dto.CreatePaymentRequest;
import com.innowise.paymentservice.dto.PaymentResponse;
import com.innowise.paymentservice.dto.TotalSumDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
  PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest);

  PaymentResponse getByOrderId(Long orderId);

  List<PaymentResponse> findAllByUserId(Long userId);

  TotalSumDto getTotalSumByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

  TotalSumDto getTotalSumByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}