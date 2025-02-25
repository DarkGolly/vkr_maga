package com.darkgolly.gps.service;

import com.darkgolly.gps.model.GpsMessage;
import com.darkgolly.gps.utils.AzimuthCalculator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketClientService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketClient webSocketClient;

    // Последние координаты для расчета курса
    private static Double lastLatitude = null;
    private static Double lastLongitude = null;

    public WebSocketClientService() {
    }

    public void connectWebSocket() {
        try {
            webSocketClient = new WebSocketClient(new URI("ws://localhost:3000/signalk/v1/stream")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to WebSocket server");
                }

                @Override
                public void onMessage(String message) {
                    //System.out.println("Received message from server: " + message);
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

    public void sendGpsMessage(GpsMessage gpsMessage) {
        try {
            // Вычисляем азимут, если есть предыдущие координаты
            Double courseOverGround = null;
            if (lastLatitude != null && lastLongitude != null) {
                courseOverGround = AzimuthCalculator.calculateHeading(
                        lastLatitude, lastLongitude, gpsMessage.getLatitude(), gpsMessage.getLongitude()
                );
            }

            // Обновляем последние координаты
            lastLatitude = gpsMessage.getLatitude();
            lastLongitude = gpsMessage.getLongitude();

            // Формируем сообщение Signal K
            Map<String, Object> message = new HashMap<>();

            // Обновление позиции
            Map<String, Object> position = new HashMap<>();
            position.put("longitude", gpsMessage.getLongitude());
            position.put("latitude", gpsMessage.getLatitude());

            Map<String, Object> positionValue = new HashMap<>();
            positionValue.put("path", "navigation.position");
            positionValue.put("value", position);

            // Обновление курса (если рассчитан)
            List<Map<String, Object>> valuesList = new ArrayList<>();
            valuesList.add(positionValue);

            if (courseOverGround != null) {
                Map<String, Object> courseValue = new HashMap<>();
                courseValue.put("path", "navigation.courseOverGroundTrue");
                courseValue.put("value", courseOverGround);
                valuesList.add(courseValue);
            }

            Map<String, Object> updates = new HashMap<>();
            updates.put("values", valuesList);
            message.put("updates", List.of(updates));

            // Отправка сообщения
            String jsonMessage = objectMapper.writeValueAsString(message);
            if (webSocketClient != null && webSocketClient.isOpen()) {
                webSocketClient.send(jsonMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}