package com.Nishant.LinkedIn_Mini.PostService.Exception;

import com.Nishant.LinkedIn_Mini.PostService.Dto.DeleteImageResponseDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostAlreadyLikedException.class)
    public ResponseEntity<ErrorResponseDto> handlePostAlreadyLiked(
            PostAlreadyLikedException ex
    ) {

        ErrorResponseDto errorResponse =
                new ErrorResponseDto(
                        ex.getMessage(),
                        HttpStatus.CONFLICT.value()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(PostDeletionException.class)
    public ResponseEntity<DeleteImageResponseDto>
    handlePostDeletionException(
            PostDeletionException ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new DeleteImageResponseDto(
                                "POST_DELETE_FAILED",
                                ex.getMessage()
                        )
                );
    }
}