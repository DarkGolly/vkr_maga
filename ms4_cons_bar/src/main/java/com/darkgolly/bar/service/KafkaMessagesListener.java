package com.darkgolly.bar.service;

import com.darkgolly.bar.model.BarMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagesListener {


    @KafkaListener(topics = "${spring.kafka.consumer.properties.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGpsTopic(BarMessage barMessage) {
        System.out.println("Received Barometer Message: " + barMessage);
        // Добавьте вашу логику обработки сообщения
    }

}