package com.example.demo;

import com.example.demo.model.BMIResponse;
import com.example.demo.model.GenericResponse;
import com.example.demo.model.HealthRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/hello")
    public GenericResponse handle(){
        return GenericResponse.builder().message("success").build();
    }

    @PostMapping("/computeBMI")
    public BMIResponse compute(@RequestBody @Valid HealthRequest healthRequest){
        Integer bmi = healthRequest.getWeightKg() / healthRequest.getHeightFeet();
        return BMIResponse.builder().bmi(bmi).build();
    }



}
