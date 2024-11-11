package com.darkgolly.gps.service;

import com.darkgolly.gps.dto.GpsMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenersExample {
    private final WebSocketClientService webSocketClientService;

    public KafkaListenersExample(WebSocketClientService webSocketClientService) {
        this.webSocketClientService = webSocketClientService;
    }

    @KafkaListener(topics = "gps_topic", groupId = "group2")
    public void listenGpsTopic(GpsMessage gpsMessage) {
        System.out.println("Received GPS Message: " + gpsMessage);
        // Добавьте вашу логику обработки сообщения
        webSocketClientService.sendGpsMessage(gpsMessage);
    }

}