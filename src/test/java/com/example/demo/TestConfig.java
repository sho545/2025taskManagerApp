package com.example.demo;

import org.dbunit.ext.h2.H2DataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.springtestdbunit.bean.DatabaseConfigBean;

@Configuration
public class TestConfig {

  @Bean
  public DatabaseConfigBean dbUnitDatabaseConfig() {
    DatabaseConfigBean config = new DatabaseConfigBean();
    config.setDatatypeFactory(new H2DataTypeFactory());
    return config;
  }
}
