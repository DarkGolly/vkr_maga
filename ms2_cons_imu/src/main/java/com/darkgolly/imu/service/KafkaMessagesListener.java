package com.darkgolly.imu.service;

import com.darkgolly.imu.model.ImuMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagesListener {

    private final WebSocketClientService webSocketClientService;

    public KafkaMessagesListener(WebSocketClientService webSocketClientService) {
        this.webSocketClientService = webSocketClientService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.properties.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGpsTopic(ImuMessage imuMessage) {
        //System.out.println("Received IMU Message: " + imuMessage);
        webSocketClientService.sendImuMessage(imuMessage);
    }

}