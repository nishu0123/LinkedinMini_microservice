package com.Nishant.LinkedIn_Mini.UploaderService.Exception;

import com.Nishant.LinkedIn_Mini.UploaderService.Dto.DeleteImageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CloudinaryDeleteException.class)
    public ResponseEntity<DeleteImageResponseDto> handleCloudinaryDeleteException(
            CloudinaryDeleteException ex) {

        DeleteImageResponseDto errorResponse = new DeleteImageResponseDto();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus("CLOUDINARY_DELETE_FAILED");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
