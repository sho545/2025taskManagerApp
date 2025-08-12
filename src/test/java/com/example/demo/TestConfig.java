package com.example.demo;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.config.CustomH2DataTypeFactory;
import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

@Configuration
public class TestConfig {

  @Bean
  public DatabaseConfigBean dbUnitDatabaseConfig() {
    DatabaseConfigBean config = new DatabaseConfigBean();
    config.setDatatypeFactory(new CustomH2DataTypeFactory());
    return config;
  }

  @Bean
  public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DataSource dataSource) {
    DatabaseDataSourceConnectionFactoryBean factory = new DatabaseDataSourceConnectionFactoryBean(dataSource);
    // 車体にエンジンを搭載する
    factory.setDatabaseConfig(dbUnitDatabaseConfig());
    return factory;
  }
}
