package com.darkgolly.prod.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GpsMessage {

    @JsonProperty("Longitude")
    private double longitude;
    @JsonProperty("Latitude")
    private double latitude;
    @JsonProperty("height")
    private int height;
    @JsonProperty("hMSL")
    private int hMSL;
    @JsonProperty("hAcc")
    private int hAcc;
    @JsonProperty("vAcc")
    private int vAcc;
}