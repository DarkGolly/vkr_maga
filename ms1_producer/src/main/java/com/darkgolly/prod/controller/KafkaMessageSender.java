package com.darkgolly.prod.controller;

import com.darkgolly.prod.service.KafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;


@RestController
@Configuration
@RequestMapping("/send")
public class KafkaMessageSender {

    @Value("${spring.kafka.topic-name}")
    String topicName;

    private final KafkaMessageProducer kafkaMessageProducer;

    @Autowired
    public KafkaMessageSender(KafkaMessageProducer kafkaMessageProducer) {
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    @PostMapping
    public String postMsg(@RequestBody Object msg){
        System.out.println("started pushing data into topic "+topicName);
        kafkaMessageProducer.sendMessage(topicName, msg);
        System.out.println("pushed successfully");
        return "success";
    }
}
