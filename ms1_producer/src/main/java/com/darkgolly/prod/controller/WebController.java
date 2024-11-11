package com.darkgolly.prod.controller;

import com.darkgolly.prod.dto.GpsMessage;
import com.darkgolly.prod.service.KafkaSenderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class WebController {

    @Autowired
    KafkaSenderExample kafkaSenderExample;

    @GetMapping("/api/v1/hello")
    private Mono<GpsMessage> hello() {

        var msg = new GpsMessage();
        msg.setNavPosllh("hello, prod!");
        msg.setNavStatus("hello, status!");

        kafkaSenderExample.sendMessage("gps_topic", msg);

        return Mono.just(msg);
    }

}
