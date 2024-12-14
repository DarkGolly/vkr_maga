package com.darkgolly.gps.service;

import com.darkgolly.gps.model.GpsMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagesListener {
    private final WebSocketClientService webSocketClientService;

    public KafkaMessagesListener(WebSocketClientService webSocketClientService) {
        this.webSocketClientService = webSocketClientService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.properties.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGpsTopic(GpsMessage gpsMessage) {
        System.out.println("Received GPS Message: " + gpsMessage);
        webSocketClientService.sendGpsMessage(gpsMessage);
    }

}