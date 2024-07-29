package com.example.demo.exceptions;

import com.example.demo.model.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BmiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse generateResponse(MethodArgumentNotValidException exception){
        return ErrorResponse.builder().errorCode("404").errorMessage(exception.getMessage()).build();
    }

}
