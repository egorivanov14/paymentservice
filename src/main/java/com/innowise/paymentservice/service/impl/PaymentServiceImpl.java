package com.innowise.paymentservice.service.impl;

import com.innowise.paymentservice.client.RandomNumberClient;
import com.innowise.paymentservice.client.dto.GetRandomIntResponse;
import com.innowise.paymentservice.dto.*;
import com.innowise.paymentservice.entity.Payment;
import com.innowise.paymentservice.exception.NotFoundException;
import com.innowise.paymentservice.kafka.PaymentServiceEventProducer;
import com.innowise.paymentservice.mapper.PaymentMapper;
import com.innowise.paymentservice.repository.PaymentRepository;
import com.innowise.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
  private final PaymentRepository paymentRepository;
  private final RandomNumberClient randomNumberClient;
  private final PaymentMapper mapper;
  private final PaymentServiceEventProducer paymentServiceEventProducer;

  public PaymentServiceImpl(PaymentRepository paymentRepository, RandomNumberClient randomNumberClient, PaymentMapper mapper, PaymentServiceEventProducer paymentServiceEventProducer) {
    this.paymentRepository = paymentRepository;
    this.randomNumberClient = randomNumberClient;
    this.mapper = mapper;
    this.paymentServiceEventProducer = paymentServiceEventProducer;
  }

  @Override
  public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) {
    GetRandomIntResponse getRandomIntResponse = randomNumberClient.generateRandomInt();
    Integer randomResultNumber = getRandomIntResponse.result().random().data().getFirst();

    PaymentStatus paymentStatus = randomResultNumber % 2 == 0 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

    Payment payment = mapper.createPaymentRequestToPayment(createPaymentRequest);
    payment.setStatus(paymentStatus);

    Payment savedPayment = paymentRepository.save(payment);

    Long orderId = savedPayment.getOrderId();
    KafkaEventDto kafkaEventDto = new KafkaEventDto(orderId, paymentStatus);
    paymentServiceEventProducer.sendPaymentEvent(kafkaEventDto);

    return mapper.paymentToPaymentResponse(savedPayment);
  }

  @Override
  public PaymentResponse getByOrderId(Long orderId) {
    Payment payment = paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new NotFoundException("Payment not found"));
    return mapper.paymentToPaymentResponse(payment);
  }

  @Override
  public List<PaymentResponse> findAllByUserId(Long userId) {
    List<Payment> payments = paymentRepository.findAllByUserId(userId);

    return payments.stream().map(mapper::paymentToPaymentResponse).toList();
  }

  @Override
  public TotalSumDto getTotalSumByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
    long totalSum = paymentRepository.getTotalSumByUserIdAndDateRange(userId, startDate, endDate);
    return new TotalSumDto(totalSum);
  }

  @Override
  public TotalSumDto getTotalSumByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
    long totalSum = paymentRepository.getTotalSumByDateRange(startDate, endDate);
    return new TotalSumDto(totalSum);
  }
}