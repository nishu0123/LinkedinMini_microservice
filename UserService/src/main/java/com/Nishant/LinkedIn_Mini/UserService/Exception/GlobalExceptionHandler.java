package com.Nishant.LinkedIn_Mini.UserService.Exception;

import com.Nishant.LinkedIn_Mini.UserService.Dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(
            InvalidCredentialsException ex,
            HttpServletRequest request) {

        ApiError apiError = new ApiError();

        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiError.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        apiError.setMessage(ex.getMessage());
        apiError.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(apiError);
    }

    @ExceptionHandler(DuplicateUserNameException.class)
    public ResponseEntity<ErrorResponseDto>
    handleDuplicateUserNameException(
            DuplicateUserNameException ex) {

        ErrorResponseDto errorResponse =
                new ErrorResponseDto(
                        "DUPLICATE_USERNAME",
                        ex.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }
}
