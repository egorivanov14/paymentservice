package com.innowise.paymentservice.controller;

import com.innowise.paymentservice.dto.CreatePaymentRequest;
import com.innowise.paymentservice.dto.PaymentResponse;
import com.innowise.paymentservice.dto.TotalSumDto;
import com.innowise.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments/")
public class PaymentController {
  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/create")
  public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest createPaymentRequest) {
    PaymentResponse paymentResponse = paymentService.createPayment(createPaymentRequest);
    return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<PaymentResponse> getPayments(@PathVariable Long orderId) {
    PaymentResponse response = paymentService.getByOrderId(orderId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<PaymentResponse>> findAllByUserId(@PathVariable Long userId) {
    List<PaymentResponse> response = paymentService.findAllByUserId(userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/total")
  public ResponseEntity<TotalSumDto> getTotalSumByUserId(
          @PathVariable Long userId,
          @RequestParam LocalDateTime startDate,
          @RequestParam LocalDateTime endDate) {
    TotalSumDto response = paymentService.getTotalSumByUserIdAndDateRange(userId, startDate, endDate);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/total")
  public TotalSumDto getTotalSumByDateRange(
          @RequestParam LocalDateTime startDate,
          @RequestParam LocalDateTime endDate) {

    return paymentService.getTotalSumByDateRange(
            startDate,
            endDate
    );
  }
}