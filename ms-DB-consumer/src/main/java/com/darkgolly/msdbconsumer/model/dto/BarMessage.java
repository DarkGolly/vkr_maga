package com.darkgolly.msdbconsumer.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BarMessage {

    @JsonProperty("Temperature")
    private double temperature;

    @JsonProperty("Pressure")
    private double pressure;
}