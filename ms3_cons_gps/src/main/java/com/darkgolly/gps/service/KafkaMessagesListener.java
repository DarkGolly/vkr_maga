package com.darkgolly.gps.service;

import com.darkgolly.gps.model.GpsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagesListener {

    private final WebSocketClientService messageSender;

    private boolean isAuthenticated = false; // Флаг авторизации
    // Метод для установки авторизации
    public void authenticate() {
        this.isAuthenticated = true;
    }
    public void logout() {
        this.isAuthenticated = false;
    }
    @Autowired
    public KafkaMessagesListener(WebSocketClientService messageSender) {
        this.messageSender = messageSender;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.properties.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGpsTopic(GpsMessage gpsMessage) {
        if (!isAuthenticated) {
            return;
        }
        messageSender.sendGpsMessage(gpsMessage);
    }
}
