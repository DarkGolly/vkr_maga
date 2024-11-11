package com.darkgolly.prod.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaSenderExample {
    public static final Logger log = LoggerFactory.getLogger(KafkaSenderExample.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    KafkaSenderExample(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(String topicName, Object message) {
        kafkaTemplate.send(topicName, message).whenComplete(
                (result, ex) -> {
                    if (ex != null) {
                        log.error("message:{} was not sent", message, ex);
                    }
                }
        );
    }

}