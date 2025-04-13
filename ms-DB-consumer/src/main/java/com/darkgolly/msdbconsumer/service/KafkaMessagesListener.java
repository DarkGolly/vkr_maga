package com.darkgolly.msdbconsumer.service;

import com.darkgolly.msdbconsumer.model.dto.BarMessage;
import com.darkgolly.msdbconsumer.model.dto.GpsMessage;
import com.darkgolly.msdbconsumer.model.entity.BarDataEntity;
import com.darkgolly.msdbconsumer.model.entity.GpsDataEntity;
import com.darkgolly.msdbconsumer.repository.BarDataRepository;
import com.darkgolly.msdbconsumer.repository.GpsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaMessagesListener {

    private final BarDataRepository barDataRepository;
    private final GpsDataRepository gpsDataRepository;



    @Autowired
    public KafkaMessagesListener(BarDataRepository barDataRepository, GpsDataRepository gpsDataRepository) {
        this.barDataRepository = barDataRepository;
        this.gpsDataRepository = gpsDataRepository;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.properties.topic.gps}", containerFactory = "gpsKafkaListenerContainerFactory")
    public void listenGpsTopic(GpsMessage gpsMessage) {

        GpsDataEntity gpsData = new GpsDataEntity(gpsMessage);
        gpsDataRepository.save(gpsData);

    }
    @KafkaListener(topics = "${spring.kafka.consumer.properties.topic.bar}", containerFactory = "barKafkaListenerContainerFactory")
    public void listenBarTopic(BarMessage barMessage) {

        BarDataEntity barData = new BarDataEntity(barMessage);
        barDataRepository.save(barData);

    }
}