package com.innowise.paymentservice.repository;

import com.innowise.paymentservice.dto.PaymentStatus;
import com.innowise.paymentservice.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, Long>, PaymentCustomRepository {
  List<Payment> findAllByUserId(Long userId);

  List<Payment> findAllByStatus(PaymentStatus status);

  Optional<Payment> findByOrderId(Long orderId);
}