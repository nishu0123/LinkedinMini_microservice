package com.Nishant.LinkedIn_Mini.PostService.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PostDislikeRequestDto {
    @NotBlank(message = "postId is required")
    Long postId;
}
