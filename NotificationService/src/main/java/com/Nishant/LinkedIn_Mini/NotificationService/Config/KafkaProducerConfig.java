package com.Nishant.LinkedIn_Mini.NotificationService.Config;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

// WRONG IMPORT (Jackson)
// import com.fasterxml.jackson.databind.JsonSerializer;

// CORRECT IMPORT (Spring Kafka)
//import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Bean
    public NewTopic sendNotificationEvent() {
        return TopicBuilder.name("followerList.Received")
                .partitions(3)
                .replicas(1)
                .build();
    }

    /*
    @Bean
    public ProducerFactory<String, SendNotificationEventDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        // Replace with your actual Kafka address if not localhost
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // This is the critical line!
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, SendNotificationEventDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    */

}


