package com.darkgolly.prod.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class ImuMessage {

    @JsonProperty("Acc")
    private List<String> acc;

    @JsonProperty("Gyr")
    private List<String> gyr;

    @JsonProperty("Mag")
    private List<String> mag;
}