package com.darkgolly.gps.service;

import com.darkgolly.gps.model.GpsMessage;
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

    public void sendGpsMessage(GpsMessage gpsMessage) {
        try {
            Map<String, Object> message = new HashMap<>();
            Map<String, Object> position = new HashMap<>();
            position.put("longitude", gpsMessage.getLongitude());
            position.put("latitude", gpsMessage.getLatitude());

            Map<String, Object> values = new HashMap<>();
            values.put("path", "navigation.position");
            values.put("value", position);

            Map<String, Object> updates = new HashMap<>();
            updates.put("values", List.of(values));
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