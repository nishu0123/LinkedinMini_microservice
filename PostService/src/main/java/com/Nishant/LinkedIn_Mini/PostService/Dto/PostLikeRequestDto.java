package com.Nishant.LinkedIn_Mini.PostService.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PostLikeRequestDto {
    @NotNull(message = "post id is required")
    @Positive(message = "post id must be a positive number")
    Long postId;
}
