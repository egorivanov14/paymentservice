package com.innowise.paymentservice.integration;

import com.innowise.paymentservice.dto.CreatePaymentRequest;
import com.innowise.paymentservice.dto.PaymentResponse;
import com.innowise.paymentservice.entity.Payment;
import com.innowise.paymentservice.repository.PaymentRepository;
import com.innowise.paymentservice.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.innowise.paymentservice.TestConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PaymentIntegrationTest extends AbstractIntegrationTest {
  @Autowired
  MongoTemplate mongoTemplate;
  @Autowired
  PaymentRepository paymentRepository;
  @Autowired
  PaymentService paymentService;


  @Test
  public void startProperties_shouldCreateCorrectMongoCollection() {
    boolean collectionExists = mongoTemplate.collectionExists(MONGO_COLLECTION_NAME);

    assertThat(collectionExists).isTrue();
  }

  @Test
  public void savePayment_shouldSavePayment() {
    Payment payment = new Payment();
    payment.setOrderId(ORDER_ID);
    payment.setUserId(USER_ID);
    payment.setStatus(PAYMENT_STATUS);
    payment.setPaymentAmount(PAYMENT_AMOUNT);

    Payment savedPayment = paymentRepository.save(payment);

    assertThat(savedPayment.getId()).isNotNull();
    assertThat(savedPayment.getStatus()).isEqualTo(PAYMENT_STATUS);
    assertThat(savedPayment.getPaymentAmount()).isEqualTo(PAYMENT_AMOUNT);
    assertThat(savedPayment.getOrderId()).isEqualTo(ORDER_ID);
    assertThat(savedPayment.getUserId()).isEqualTo(USER_ID);
  }

  @Test
  public void create_shouldCreatePayment() {
    CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(ORDER_ID, USER_ID, PAYMENT_AMOUNT);

    PaymentResponse paymentResponse = paymentService.createPayment(createPaymentRequest);

    assertThat(paymentResponse).isNotNull();
    assertThat(paymentResponse.paymentAmount()).isEqualTo(PAYMENT_AMOUNT);
    assertThat(paymentResponse.userId()).isEqualTo(USER_ID);
    assertThat(paymentResponse.orderId()).isEqualTo(ORDER_ID);
    System.out.println(paymentResponse.status());
  }
}