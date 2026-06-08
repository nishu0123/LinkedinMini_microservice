package com.Nishant.LinkedIn_Mini.UserService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class ErrorResponseDto {

    //implement the constructor
    public void ErrorResponseDto()
    {

    }
//    either set all the property or does not set any
//    public void ErrorResponseDto(String errorCode)
//    {
//        this.errorCode = errorCode;
//    }

    public void ErrorResponseDto(String errorCode , String message)
    {
        this.errorCode = errorCode;
        this.message = message;
    }


    private String errorCode;
    private String message;
}