package com.Nishant.LinkedIn_Mini.UserService.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiError buildApiError(
            HttpStatus status,
            String message,
            HttpServletRequest request) {
        ApiError apiError = new ApiError();

        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(status.value());
        apiError.setError(status.getReasonPhrase());
        apiError.setMessage(message);
        apiError.setPath(request.getRequestURI());

        return apiError;
    }


    @ExceptionHandler(DuplicateUserNameException.class)
    public ResponseEntity<ApiError> handleDuplicateUserNameException(
            DuplicateUserNameException ex,
            HttpServletRequest request) {

        ApiError apiError = buildApiError(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiError);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentialsException(
            InvalidCredentialsException ex,
            HttpServletRequest request) {

        ApiError apiError = buildApiError(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(apiError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(
            UserNotFoundException ex,
            HttpServletRequest request) {

        //set the status
        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiError apiError = buildApiError(
                status,
                ex.getMessage(),
                request
        );

        return ResponseEntity
                .status(status)
                .body(apiError);
    }




//    @ExceptionHandler(DuplicateUserNameException.class)
//    public ResponseEntity<ErrorResponseDto>
//    handleDuplicateUserNameException(
//            DuplicateUserNameException ex) {
//
//        ErrorResponseDto errorResponse =
//                new ErrorResponseDto(
//                        "DUPLICATE_USERNAME",
//                        ex.getMessage()
//                );
//
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT)
//                .body(errorResponse);
//    }
}
