package com.Nishant.LinkedIn_Mini.PostService.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public NewTopic postCreatedTopic() {
        return TopicBuilder.name("post.created")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic postLikedTopic() {
        return TopicBuilder.name("post.liked")
                .partitions(3)
                .replicas(1)
                .build();
    }
    /*
    without topic builder we can also create a bean
    by just returning like
    parameter - name , partition , replica
    return new newTopic ("post.liked" , 3 , 1)

     */
}
