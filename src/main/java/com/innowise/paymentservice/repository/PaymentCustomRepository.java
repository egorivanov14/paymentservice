package com.innowise.paymentservice.repository;

import com.innowise.paymentservice.dto.TotalSumDto;

import java.time.LocalDateTime;

public interface PaymentCustomRepository {
  TotalSumDto getTotalSumByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

  TotalSumDto getTotalSumByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}