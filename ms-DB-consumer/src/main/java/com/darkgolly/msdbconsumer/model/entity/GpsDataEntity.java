package com.darkgolly.msdbconsumer.model.entity;

import com.darkgolly.msdbconsumer.model.dto.GpsMessage;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "gps_data")
@Data
public class GpsDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "height")
    private double height;

    @Column(name = "h_msl")
    private double hMSL;

    @Column(name = "h_acc")
    private double hAcc;

    @Column(name = "v_acc")
    private double vAcc;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    // Конструктор для маппинга из GpsMessage
    public GpsDataEntity(GpsMessage gpsMessage) {
        this.longitude = gpsMessage.getLongitude();
        this.latitude = gpsMessage.getLatitude();
        this.height = gpsMessage.getHeight();
        this.hMSL = gpsMessage.getHMSL();
        this.hAcc = gpsMessage.getHAcc();
        this.vAcc = gpsMessage.getVAcc();
        this.timestamp = LocalDateTime.now(); // Добавляем текущую временную метку
    }

    public GpsDataEntity() {
    }
}