package com.Nishant.LinkedIn_Mini.PostService.Util;

import com.nishant.linkedinmini.common.contracts.ApiResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseBuilder {

    public static <T> ApiResponse<T> buildSuccessResponse(
            HttpStatus status,
            String message,
            T data) {

        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(message)
                .data(data)
                .build();
    }
}