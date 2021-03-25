package com.veichle.app.controller;

import com.veichle.app.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntimeException(HttpServletRequest request, HttpServletResponse response, RuntimeException exception){
        log.info(exception.getMessage());
        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE,exception.getMessage());
    }
}