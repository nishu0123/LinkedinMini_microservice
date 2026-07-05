package com.Nishant.LinkedIn_Mini.PostService.Exception;

import com.Nishant.LinkedIn_Mini.PostService.Dto.DeleteImageResponseDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.ErrorResponseDto;
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

    @ExceptionHandler(PostAlreadyLikedException.class)
    public ResponseEntity<ApiError> handlePostAlreadyLiked(
            PostAlreadyLikedException ex , HttpServletRequest request
    ) {


        ApiError apiError = buildApiError(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiError);

    }

    @ExceptionHandler(PostDeletionException.class)
    public ResponseEntity<ApiError>
    handlePostDeletionException(
            PostDeletionException ex , HttpServletRequest request) {

        ApiError apiError = buildApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);

    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<ApiError>
    handleInvalidImageException(
            InvalidImageException ex , HttpServletRequest request) {

        ApiError apiError = buildApiError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);

    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiError>
    handleInvalidImageException(
            PostNotFoundException ex , HttpServletRequest request) {

        ApiError apiError = buildApiError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);

    }
}