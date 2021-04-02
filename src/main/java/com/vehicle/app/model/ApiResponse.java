package com.vehicle.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>{

    int code=200;
    private HttpStatus status=HttpStatus.OK;
    private T data;
    private String message;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(HttpStatus status, String message) {
        this.code=status.value();
        this.status = status;
        this.message = message;
    }
}

