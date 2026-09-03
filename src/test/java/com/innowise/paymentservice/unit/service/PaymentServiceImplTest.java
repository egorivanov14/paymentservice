package com.innowise.paymentservice.unit.service;

import com.innowise.paymentservice.client.RandomNumberClient;
import com.innowise.paymentservice.dto.CreatePaymentRequest;
import com.innowise.paymentservice.dto.PaymentResponse;
import com.innowise.paymentservice.dto.TotalSumDto;
import com.innowise.paymentservice.entity.Payment;
import com.innowise.paymentservice.exception.NotFoundException;
import com.innowise.paymentservice.kafka.PaymentServiceEventProducer;
import com.innowise.paymentservice.mapper.PaymentMapper;
import com.innowise.paymentservice.repository.PaymentRepository;
import com.innowise.paymentservice.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.innowise.paymentservice.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
  @Mock
  private PaymentRepository paymentRepository;
  @Mock
  private RandomNumberClient randomNumberClient;
  @Mock
  private PaymentMapper paymentMapper;
  @Mock
  private PaymentServiceEventProducer paymentServiceEventProducer;

  @InjectMocks
  private PaymentServiceImpl paymentService;

  @Test
  public void createPayment_shouldCreatePayment() {
    CreatePaymentRequest request = new CreatePaymentRequest(ORDER_ID, USER_ID, PAYMENT_AMOUNT);
    Payment payment = new Payment();
    PaymentResponse response = new PaymentResponse(ID, ORDER_ID, USER_ID, SUCCESS, LocalDateTime.now(), PAYMENT_AMOUNT);

    when(randomNumberClient.generateRandomInt()).thenReturn(RANDOM_INT_RESPONSE);
    when(paymentMapper.createPaymentRequestToPayment(request)).thenReturn(payment);
    when(paymentRepository.save(payment)).thenReturn(payment);
    when(paymentMapper.paymentToPaymentResponse(payment)).thenReturn(response);

    PaymentResponse actual = paymentService.createPayment(request);
    assertEquals(response, actual);
  }

  @Test
  void getByOrderId_shouldReturnPayment() {
    Payment payment = new Payment();
    PaymentResponse response = new PaymentResponse(ID, ORDER_ID, USER_ID, SUCCESS, LocalDateTime.now(), PAYMENT_AMOUNT);

    when(paymentRepository.findByOrderId(ORDER_ID)).thenReturn(Optional.of(payment));
    when(paymentMapper.paymentToPaymentResponse(payment)).thenReturn(response);

    PaymentResponse actual = paymentService.getByOrderId(ORDER_ID);

    assertEquals(response, actual);
  }

  @Test
  void getByOrderId_notFound_shouldThrowNotFoundException() {
    when(paymentRepository.findByOrderId(ORDER_ID)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> paymentService.getByOrderId(ORDER_ID));
  }

  @Test
  void findAllByUserId_shouldReturnPayments() {
    Payment payment = new Payment();
    PaymentResponse response = new PaymentResponse(ID, ORDER_ID, USER_ID, SUCCESS, LocalDateTime.now(), PAYMENT_AMOUNT);

    when(paymentRepository.findAllByUserId(USER_ID)).thenReturn(List.of(payment));
    when(paymentMapper.paymentToPaymentResponse(payment)).thenReturn(response);

    List<PaymentResponse> actual = paymentService.findAllByUserId(USER_ID);

    assertEquals(1, actual.size());
    assertEquals(response, actual.getFirst());
  }

  @Test
  void findAllByUserId_noPayments_shouldReturnEmptyList() {
    when(paymentRepository.findAllByUserId(USER_ID)).thenReturn(List.of());

    List<PaymentResponse> actual = paymentService.findAllByUserId(USER_ID);

    assertTrue(actual.isEmpty());
    verify(paymentMapper, never()).paymentToPaymentResponse(any());
  }

  @Test
  void getTotalSumByUserIdAndDateRange_shouldReturnTotalSum() {
    TotalSumDto expected = new TotalSumDto(300L);

    LocalDateTime startDate = LocalDateTime.now().minusDays(1);
    LocalDateTime endDate = LocalDateTime.now().plusDays(1);

    when(paymentRepository.getTotalSumByUserIdAndDateRange(USER_ID, startDate, endDate))
            .thenReturn(expected);

    TotalSumDto actual = paymentService.getTotalSumByUserIdAndDateRange(USER_ID, startDate, endDate);

    assertEquals(expected, actual);
    verify(paymentRepository).getTotalSumByUserIdAndDateRange(USER_ID, startDate, endDate);
  }

  @Test
  void getTotalSumByDateRange_shouldReturnTotalSum() {
    TotalSumDto expected = new TotalSumDto(500L);

    LocalDateTime startDate = LocalDateTime.now().minusDays(1);
    LocalDateTime endDate = LocalDateTime.now().plusDays(1);

    when(paymentRepository.getTotalSumByDateRange(startDate, endDate))
            .thenReturn(expected);

    TotalSumDto actual = paymentService.getTotalSumByDateRange(startDate, endDate);

    assertEquals(expected, actual);
    verify(paymentRepository).getTotalSumByDateRange(startDate, endDate);
  }
}