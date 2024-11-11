package com.example.demo.service;

import com.example.demo.dto.BarMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenersExample {


    @KafkaListener(topics = "bar_topic", groupId = "group2")
    public void listenGpsTopic(BarMessage barMessage) {
        System.out.println("Received Barometer Message: " + barMessage);
        // Добавьте вашу логику обработки сообщения
    }

}