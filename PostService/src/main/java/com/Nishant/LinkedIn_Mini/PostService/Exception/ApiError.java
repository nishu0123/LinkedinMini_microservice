package com.Nishant.LinkedIn_Mini.PostService.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus httpStatus;
    //use  constructor to set the timestamp
    public ApiError(){this.timeStamp = LocalDateTime.now();}
    public ApiError(String error , HttpStatus httpStatus)
    {
        this();
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
