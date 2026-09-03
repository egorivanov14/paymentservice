package com.innowise.paymentservice.integration;

import com.innowise.paymentservice.dto.CreatePaymentRequest;
import com.innowise.paymentservice.entity.Payment;
import com.innowise.paymentservice.exception.NotFoundException;
import com.innowise.paymentservice.kafka.PaymentServiceEventProducer;
import com.innowise.paymentservice.repository.PaymentRepository;
import com.innowise.paymentservice.service.PaymentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static com.innowise.paymentservice.TestConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentIntegrationTest extends AbstractIntegrationTest {
  @Autowired
  WebApplicationContext webApplicationContext;
  @Autowired
  PaymentRepository paymentRepository;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private PaymentService paymentService;
  @Autowired
  private MongoTemplate mongoTemplate;
  private MockMvc mockMvc;

  @MockitoBean
  PaymentServiceEventProducer paymentServiceEventProducer;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build();
  }

  @AfterEach
  void tearDown() {
    paymentRepository.deleteAll();
  }

  @Test
  public void create_shouldCreatePayment() throws Exception {
    CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(ORDER_ID, USER_ID, PAYMENT_AMOUNT);
    createPayment(createPaymentRequest);

    Payment payment = paymentRepository.findByOrderId(ORDER_ID).orElseThrow(() -> new NotFoundException("Payment not found"));

    assertThat(payment.getPaymentAmount()).isEqualTo(PAYMENT_AMOUNT);
    assertThat(payment.getUserId()).isEqualTo(USER_ID);
    assertThat(payment.getOrderId()).isEqualTo(ORDER_ID);
  }

  @Test
  public void getByOrderId_shouldReturnPayment() throws Exception {
    CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(ORDER_ID, USER_ID, PAYMENT_AMOUNT);
    paymentService.createPayment(createPaymentRequest);

    mockMvc.perform(get("/api/payments/order/{id}", ORDER_ID)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orderId").value(ORDER_ID))
            .andExpect(jsonPath("$.userId").value(USER_ID))
            .andExpect(jsonPath("$.paymentAmount").value(PAYMENT_AMOUNT));
  }

  @Test
  public void findAllByUserId_shouldReturnPayments() throws Exception {
    CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(ORDER_ID, USER_ID, PAYMENT_AMOUNT);
    paymentService.createPayment(createPaymentRequest);
    paymentService.createPayment(createPaymentRequest);
    paymentService.createPayment(createPaymentRequest);

    mockMvc.perform(get("/api/payments/user/{userId}", USER_ID)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty());
  }

  @Test
  public void getTotalSumByUserIdAndDateRange_shouldReturnPayments() throws Exception {
    CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(ORDER_ID, USER_ID, PAYMENT_AMOUNT);
    paymentService.createPayment(createPaymentRequest);
    paymentService.createPayment(createPaymentRequest);
    paymentService.createPayment(createPaymentRequest);

    LocalDateTime startDate = LocalDateTime.now().minusDays(1);
    LocalDateTime endDate = LocalDateTime.now().plusDays(1);

    mockMvc.perform(get("/api/payments/user/{userId}/total", USER_ID)
                    .param("startDate", startDate.toString())
                    .param("endDate", endDate.toString())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalSum").value(PAYMENT_AMOUNT * 3));
  }

  @Test
  public void getTotalSumByDateRange_shouldReturnPayments() throws Exception {
    CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(ORDER_ID, USER_ID, PAYMENT_AMOUNT);
    paymentService.createPayment(createPaymentRequest);
    paymentService.createPayment(createPaymentRequest);
    paymentService.createPayment(createPaymentRequest);

    LocalDateTime startDate = LocalDateTime.now().minusDays(1);
    LocalDateTime endDate = LocalDateTime.now().plusDays(1);

    mockMvc.perform(get("/api/payments/total")
                    .param("startDate", startDate.toString())
                    .param("endDate", endDate.toString())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalSum").value(PAYMENT_AMOUNT * 3));
  }

  private void createPayment(CreatePaymentRequest createPaymentRequest) throws Exception {
    String jsonCreatePaymentRequest = objectMapper.writeValueAsString(createPaymentRequest);

    mockMvc.perform(post("/api/payments/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonCreatePaymentRequest))
            .andExpect(status().isOk());
  }
}