package com.Nishant.LinkedIn_Mini.NotificationService.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.JacksonJsonMessageConverter;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public RecordMessageConverter converter() {
        // Replacement for JsonMessageConverter/StringJsonMessageConverter
        return new JacksonJsonMessageConverter();
    }
}