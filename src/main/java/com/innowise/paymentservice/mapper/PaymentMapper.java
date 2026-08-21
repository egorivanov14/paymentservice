package com.innowise.paymentservice.mapper;

import com.innowise.paymentservice.dto.CreatePaymentRequest;
import com.innowise.paymentservice.dto.PaymentResponse;
import com.innowise.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "timestamp", ignore = true)
  Payment createPaymentRequestToPayment(CreatePaymentRequest request);

  PaymentResponse paymentToPaymentResponse(Payment payment);
}