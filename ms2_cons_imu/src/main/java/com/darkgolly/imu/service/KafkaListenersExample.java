package com.darkgolly.imu.service;

import com.darkgolly.imu.dto.ImuMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenersExample {


    @KafkaListener(topics = "imu_topic", groupId = "myGroup")
    public void listenGpsTopic(ImuMessage imuMessage) {
        System.out.println("Received IMU Message: " + imuMessage);
        // Добавьте вашу логику обработки сообщения
    }

}