package com.darkgolly.prod.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImuMessage {

    @JsonProperty("Acc")
    private List<Double> acc;

    @JsonProperty("Gyr")
    private List<Double> gyr;

    @JsonProperty("Mag")
    private List<Double> mag;
}