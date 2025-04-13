package com.darkgolly.msdbconsumer.model.entity;

import com.darkgolly.msdbconsumer.model.dto.BarMessage;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bar_data")
@Data
public class BarDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "pressure")
    private double pressure;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public BarDataEntity(BarMessage barMessage){
        this.temperature = barMessage.getTemperature();
        this.pressure = barMessage.getPressure();
        this.timestamp = LocalDateTime.now();
    }

    public BarDataEntity() {

    }
}
