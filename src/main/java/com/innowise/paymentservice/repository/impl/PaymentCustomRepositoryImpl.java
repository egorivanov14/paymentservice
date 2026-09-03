package com.innowise.paymentservice.repository.impl;

import com.innowise.paymentservice.dto.TotalSumDto;
import com.innowise.paymentservice.repository.PaymentCustomRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.innowise.paymentservice.configuration.Constants.*;

@Repository
public class PaymentCustomRepositoryImpl implements PaymentCustomRepository {

  private final MongoTemplate mongoTemplate;

  public PaymentCustomRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public TotalSumDto getTotalSumByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
    Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(
                    Criteria.where(USER_ID_MONGO_KEY).is(userId)
                            .and(TIMESTAMP_MONGO_KEY)
                            .gte(startDate)
                            .lte(endDate)
            ),
            Aggregation.group()
                    .sum(PAYMENT_AMOUNT_MONGO_KEY)
                    .as(TOTAL_SUM_VARIABLE)
    );

    AggregationResults<TotalSumDto> results = mongoTemplate
            .aggregate(aggregation, PAYMENT_MONGO_COLLECTION_NAME, TotalSumDto.class);

    TotalSumDto result = results.getUniqueMappedResult();
    return result != null ? result : new TotalSumDto(0L);
  }

  @Override
  public TotalSumDto getTotalSumByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
    Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(
                    Criteria.where(TIMESTAMP_MONGO_KEY)
                            .gte(startDate)
                            .lte(endDate)
            ),
            Aggregation.group()
                    .sum(PAYMENT_AMOUNT_MONGO_KEY)
                    .as(TOTAL_SUM_VARIABLE)
    );

    AggregationResults<TotalSumDto> results = mongoTemplate
            .aggregate(aggregation, PAYMENT_MONGO_COLLECTION_NAME, TotalSumDto.class);

    TotalSumDto result = results.getUniqueMappedResult();
    return result != null ? result : new TotalSumDto(0L);
  }
}