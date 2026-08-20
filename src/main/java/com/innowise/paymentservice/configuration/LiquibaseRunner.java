package com.innowise.paymentservice.configuration;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LiquibaseRunner implements ApplicationRunner {
  @Value("${spring.liquibase.url}")
  private String url;

  @Value("${spring.liquibase.change-log}")
  private String changeLog;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Database database = DatabaseFactory.getInstance()
            .openDatabase(url, null, null, null, new ClassLoaderResourceAccessor());

    try (Liquibase liquibase = new Liquibase(changeLog, new ClassLoaderResourceAccessor(), database)) {
      liquibase.update(new Contexts(), new LabelExpression());
    }
  }
}