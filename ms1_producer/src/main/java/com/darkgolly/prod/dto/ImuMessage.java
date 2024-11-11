package com.darkgolly.prod.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

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