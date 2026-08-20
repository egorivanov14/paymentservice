package com.innowise.paymentservice.repository;

import java.time.LocalDateTime;

public interface PaymentCustomRepository {
  long getTotalSumByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

  long getTotalSumByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}