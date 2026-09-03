package com.innowise.paymentservice.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import static com.innowise.paymentservice.TestConstants.MONGO_DOCKER_IMAGE_NAME;
import static com.innowise.paymentservice.TestConstants.RANDOM_NUMBER_CLIENT_STUB_JSON;

@Testcontainers
@SpringBootTest
public class AbstractIntegrationTest {
  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGO_DOCKER_IMAGE_NAME);

  @Container
  static WireMockContainer wireMockContainer = new WireMockContainer("wiremock/wiremock:3.13.2")
          .withMappingFromJSON(RANDOM_NUMBER_CLIENT_STUB_JSON);

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add(
            "spring.mongodb.uri",
            mongoDBContainer::getReplicaSetUrl
    );

    registry.add(
            "spring.liquibase.url",
            mongoDBContainer::getReplicaSetUrl
    );

    registry.add(
            "spring.kafka.admin.auto-create",
            () -> "false"
    );

    registry.add(
            "client.url",
            wireMockContainer::getBaseUrl
    );
  }
}