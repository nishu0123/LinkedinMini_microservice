package com.Nishant.LinkedIn_Mini.PostService.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {

    private String message;
    private int statusCode;
}