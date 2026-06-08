package com.Nishant.LinkedIn_Mini.ConnectionService.Exception;

import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserNameException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUserNameException(
            DuplicateUserNameException ex) {



        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "DUPLICATE_USERNAME",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(errorResponse);
    }
}