package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BMIResponse {
    @JsonProperty("BMI")
    private Integer bmi;

}
