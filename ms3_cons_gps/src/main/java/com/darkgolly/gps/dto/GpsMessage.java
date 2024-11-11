package com.darkgolly.gps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GpsMessage {

    @JsonProperty("NAV_POSLLH")
    private String navPosllh;

    @JsonProperty("NAV_STATUS")
    private String navStatus;
}