package com.attornatus.api.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private T data;
    private String message;
    private HttpStatus status;

    //Success
    public ApiResponse(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

    //Error
    public ApiResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}