package com.darkgolly.msdbconsumer.service;

import com.darkgolly.msdbconsumer.model.dto.GpsMessage;
import com.darkgolly.msdbconsumer.model.entity.GpsDataEntity;
import com.darkgolly.msdbconsumer.repository.GpsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaMessagesListener {

    private final GpsDataRepository gpsDataRepository;

    private boolean isAuthenticated = false;

    @Autowired
    public KafkaMessagesListener(GpsDataRepository gpsDataRepository) {
        this.gpsDataRepository = gpsDataRepository;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.properties.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGpsTopic(GpsMessage gpsMessage) {
        if (!isAuthenticated) {
            return;
        }

        GpsDataEntity gpsData = new GpsDataEntity(gpsMessage);
        gpsDataRepository.save(gpsData);

    }
}