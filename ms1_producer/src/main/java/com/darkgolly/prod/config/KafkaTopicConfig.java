package com.darkgolly.prod.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic gpsTopic() {
        return TopicBuilder.name("gps_topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic imuTopic() {
        return TopicBuilder.name("imu_topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic barTopic() {
        return TopicBuilder.name("bar_topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}

