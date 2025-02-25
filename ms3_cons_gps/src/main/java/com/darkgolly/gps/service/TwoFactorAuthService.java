package com.darkgolly.gps.service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

@Service
public class TwoFactorAuthService {

    private final TimeBasedOneTimePasswordGenerator totpGenerator;

    public TwoFactorAuthService() throws NoSuchAlgorithmException {
        this.totpGenerator = new TimeBasedOneTimePasswordGenerator(Duration.ofSeconds(30));
    }

    // Генерация секретного ключа
    public String generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(totpGenerator.getAlgorithm());
            keyGenerator.init(160, new SecureRandom());
            Key key = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка генерации ключа", e);
        }
    }

    // Генерация ссылки для Google Authenticator
    public String getOtpAuthUrl(String secretKey) {
        return "otpauth://totp/MyApp?secret=" + secretKey + "&issuer=MyCompany";
    }

    // Проверка введенного кода
    public boolean validateOtp(String secretKey, String otp) {
        try {
            Key key = new javax.crypto.spec.SecretKeySpec(Base64.getDecoder().decode(secretKey), totpGenerator.getAlgorithm());
            int generatedOtp = totpGenerator.generateOneTimePassword(key, Instant.now());
            return Integer.parseInt(otp) == generatedOtp;
        } catch (Exception e) {
            return false;
        }
    }
}