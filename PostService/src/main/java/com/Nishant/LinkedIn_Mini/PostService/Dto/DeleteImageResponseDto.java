package com.Nishant.LinkedIn_Mini.PostService.Dto;

import lombok.Data;

@Data
public class DeleteImageResponseDto {
    private String status;
    private String message;

    public DeleteImageResponseDto(String postDeleteFailed, String message) {
    }
}
