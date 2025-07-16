package com.example.demo.config;

import org.apache.ibatis.type.UUIDTypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    /**
     * MyBatisのグローバル設定をカスタマイズします。
     * ここでは、UUIDを正しく扱うためのTypeHandlerを登録しています。
     * @return ConfigurationCustomizer
     */
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            // java.util.UUID クラスを扱うための TypeHandler を登録
            configuration.getTypeHandlerRegistry().register(java.util.UUID.class, UUIDTypeHandler.class);
        };
    }
}
