package com.innowise.paymentservice.client;

import com.innowise.paymentservice.client.dto.GetRandomIntNumberParams;
import com.innowise.paymentservice.client.dto.GetRandomIntNumberRequest;
import com.innowise.paymentservice.client.dto.GetRandomIntResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static com.innowise.paymentservice.configuration.Constants.*;

@Service
public class RandomNumberClient {

  private final RestClient restClient;

  public RandomNumberClient(RestClient restClient) {
    this.restClient = restClient;
  }

  public GetRandomIntResponse generateRandomInt() {
    GetRandomIntNumberParams params = new GetRandomIntNumberParams(RANDOM_CLIENT_API_KEY,
            RANDOM_NUMBERS_QUANTITY,
            RANDOM_MIN_NUMBER,
            RANDOM_MAX_NUMBER);
    GetRandomIntNumberRequest getRandomIntNumberRequest = new GetRandomIntNumberRequest(JSONRPC,
            RANDOM_CLIENT_METHOD,
            params,
            RANDOM_CLIENT_REQUEST_ID);

    return restClient
            .post()
            .body(getRandomIntNumberRequest)
            .retrieve()
            .onStatus(HttpStatusCode::isError,
                    ((request, response) -> {
                      throw new RuntimeException();//todo custom exception
                    }))
            .toEntity(GetRandomIntResponse.class)
            .getBody();
  }
}