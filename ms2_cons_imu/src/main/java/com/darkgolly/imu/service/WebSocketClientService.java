package com.darkgolly.imu.service;


import com.darkgolly.imu.model.ImuMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketClientService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketClient webSocketClient;

    public WebSocketClientService() {
        connectWebSocket();
    }

    private void connectWebSocket() {
        try {
            webSocketClient = new WebSocketClient(new URI("ws://localhost:3000/signalk/v1/stream")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to WebSocket server");
                }
                @Override
                public void onMessage(String message) {
                    System.out.println("Received message from server: " + message);
                }
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Disconnected from WebSocket server: " + reason);
                }
                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            webSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendImuMessage(ImuMessage imuMessage) {
        try {
            Map<String, Object> message = new HashMap<>();

// Создаем структуру для acc
            Map<String, Object> acc = new HashMap<>();
            acc.put("x", imuMessage.getAcc().get(0));
            acc.put("y", imuMessage.getAcc().get(1));
            acc.put("z", imuMessage.getAcc().get(2));

            Map<String, Object> accValues = new HashMap<>();
            accValues.put("path", "acc");
            accValues.put("value", acc);

// Создаем структуру для gyr
            Map<String, Object> gyr = new HashMap<>();
            gyr.put("x", imuMessage.getGyr().get(0));
            gyr.put("y", imuMessage.getGyr().get(1));
            gyr.put("z", imuMessage.getGyr().get(2));

            Map<String, Object> gyrValues = new HashMap<>();
            gyrValues.put("path", "gyr");
            gyrValues.put("value", gyr);

// Создаем структуру для mag
            Map<String, Object> mag = new HashMap<>();
            mag.put("x", imuMessage.getMag().get(0));
            mag.put("y", imuMessage.getMag().get(1));
            mag.put("z", imuMessage.getMag().get(2));

            Map<String, Object> magValues = new HashMap<>();
            magValues.put("path", "mag");
            magValues.put("value", mag);

// Объединяем все значения в updates
            Map<String, Object> updates = new HashMap<>();
            updates.put("values", List.of(accValues, gyrValues, magValues));

// Финальное сообщение
            message.put("updates", List.of(updates));
            String jsonMessage = objectMapper.writeValueAsString(message);
            if (webSocketClient != null && webSocketClient.isOpen()) {
                webSocketClient.send(jsonMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}