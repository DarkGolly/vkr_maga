package com.darkgolly.gps.controller;

import com.darkgolly.gps.service.KafkaMessagesListener;
import com.darkgolly.gps.service.TwoFactorAuthService;
import com.darkgolly.gps.service.WebSocketClientService;
import com.darkgolly.gps.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/auth")
public class TwoFactorAuthController {

    private final TwoFactorAuthService twoFactorAuthService;
    private final KafkaMessagesListener kafkaMessagesListener;
    private final WebSocketClientService webSocketClientService;


    public TwoFactorAuthController(TwoFactorAuthService twoFactorAuthService, KafkaMessagesListener kafkaMessagesListener, WebSocketClientService webSocketClientService) {
        this.twoFactorAuthService = twoFactorAuthService;
        this.kafkaMessagesListener = kafkaMessagesListener;
        this.webSocketClientService = webSocketClientService;
    }

    // Показываем страницу авторизации
    @GetMapping
    public String showAuthPage() {
        return "auth";
    }

    // Генерация QR-кода для Google Authenticator
    @GetMapping("/generate-qr")
    public void generateQRCode(HttpSession session, OutputStream outputStream) throws WriterException, IOException {
        String secretKey = twoFactorAuthService.generateSecretKey();
        session.setAttribute("secretKey", secretKey); // Сохраняем ключ в сессии

        String otpAuthURL = twoFactorAuthService.getOtpAuthUrl(secretKey);
        BufferedImage image = QRCodeGenerator.generateQRCodeImage(otpAuthURL);

        ImageIO.write(image, "PNG", outputStream);
    }

    // Проверка введенного кода
    @PostMapping("/verify")
    public String verifyOtp(@RequestParam("otp") String otp, HttpSession session, RedirectAttributes redirectAttributes) {
        String secretKey = (String) session.getAttribute("secretKey");

        if (secretKey != null && twoFactorAuthService.validateOtp(secretKey, otp)) {
            kafkaMessagesListener.authenticate(); // Запускаем Kafka listener
            webSocketClientService.connectWebSocket(); // Подключаем WebSocket
            return "redirect:/home"; // Перенаправляем на основную страницу
        } else {
            redirectAttributes.addFlashAttribute("error", "Неверный код. Попробуйте снова.");
            return "redirect:/auth";
        }
    }
}
