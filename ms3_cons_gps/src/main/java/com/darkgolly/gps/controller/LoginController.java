package com.darkgolly.gps.controller;

//import org.springframework.security.core.Authentication;
import com.darkgolly.gps.service.KafkaMessagesListener;
import com.darkgolly.gps.service.WebSocketClientService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {
    private final KafkaMessagesListener kafkaMessagesListener;
    private final WebSocketClientService webSocketClientService;

    public LoginController(KafkaMessagesListener kafkaMessagesListener, WebSocketClientService webSocketClientService) {
        this.kafkaMessagesListener = kafkaMessagesListener;
        this.webSocketClientService = webSocketClientService;
    }

    @GetMapping("/home")
    public String home() {
        return "home.html";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, Principal principal) {
        kafkaMessagesListener.authenticate(); // Запускаем Kafka listener
        webSocketClientService.connectToSignalK(); // Подключаем WebSocket
        model.addAttribute("username", principal.getName());
        return "welcome.html";
    }

    @GetMapping("/login")
    public String login() {
        kafkaMessagesListener.logout();
        webSocketClientService.disconnectToSignalK();
        return "login.html";
    }
}
