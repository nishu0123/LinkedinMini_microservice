package com.Nishant.LinkedIn_Mini.UploaderService.Exception;

import com.Nishant.LinkedIn_Mini.UploaderService.Dto.DeleteImageResponseDto;
import com.nishant.linkedinmini.common.contracts.Dto.Exception.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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


    @ExceptionHandler(CloudinaryDeleteException.class)
    public ResponseEntity<ApiError> handleCloudinaryDeleteException(
            CloudinaryDeleteException ex ,HttpServletRequest request) {

        ApiError apiError = buildApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);

    }
}
