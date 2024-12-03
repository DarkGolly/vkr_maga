package com.darkgolly.prod.service;

import com.darkgolly.prod.model.BarMessage;
import com.darkgolly.prod.model.GpsMessage;
import com.darkgolly.prod.model.ImuMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private KafkaMessageProducer kafkaSenderExample;
    private final ObjectMapper objectMapper;

    @Autowired
    public MyWebSocketHandler() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        if (payload.contains("Longitude")) {
            GpsMessage gpsMessage = objectMapper.readValue(payload, GpsMessage.class);
            kafkaSenderExample.sendMessage("gps_topic", gpsMessage);
            // System.out.println("Received GPS Message: " + gpsMessage);
        } else if (payload.contains("Acc") || payload.contains("Gyr") || payload.contains("Mag")) {
            ImuMessage imuMessage = objectMapper.readValue(payload, ImuMessage.class);
            kafkaSenderExample.sendMessage("imu_topic", imuMessage);
            // System.out.println("Received IMU Message: " + imuMessage);
        } else if (payload.contains("Temperature") || payload.contains("Pressure")) {
            BarMessage barMessage = objectMapper.readValue(payload, BarMessage.class);
            kafkaSenderExample.sendMessage("bar_topic", barMessage);
            // System.out.println("Received Bar Message: " + barMessage);
        } else {
            System.out.println("Received unknown message type");
        }
    }
}