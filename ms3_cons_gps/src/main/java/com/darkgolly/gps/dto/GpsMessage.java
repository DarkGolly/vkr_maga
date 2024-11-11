package com.darkgolly.gps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GpsMessage {

    @JsonProperty("Longitude")
    private double longitude;
    @JsonProperty("Latitude")
    private double latitude;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("hMSL")
    private Integer hMSL;
    @JsonProperty("hAcc")
    private Integer hAcc;
    @JsonProperty("vAcc")
    private Integer vAcc;
}