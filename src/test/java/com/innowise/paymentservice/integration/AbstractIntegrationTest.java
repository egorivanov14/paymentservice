package com.innowise.paymentservice.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.innowise.paymentservice.TestConstants.MONGO_DOCKER_IMAGE_NAME;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class AbstractIntegrationTest {
  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGO_DOCKER_IMAGE_NAME);
}