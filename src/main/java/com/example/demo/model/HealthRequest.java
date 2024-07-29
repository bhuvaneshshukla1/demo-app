package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class HealthRequest {

    @JsonProperty("height")
    @NotNull
    private Integer heightFeet;
    @JsonProperty("weight")
    @NotNull
    private Integer weightKg;


}
