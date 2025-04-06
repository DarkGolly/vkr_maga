package com.darkgolly.msdbconsumer.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GpsMessage {

    @JsonProperty("Longitude")
    private double longitude;
    @JsonProperty("Latitude")
    private double latitude;
    @JsonProperty("height")
    private double height;
    @JsonProperty("hMSL")
    private double hMSL;
    @JsonProperty("hAcc")
    private double hAcc;
    @JsonProperty("vAcc")
    private double vAcc;
}