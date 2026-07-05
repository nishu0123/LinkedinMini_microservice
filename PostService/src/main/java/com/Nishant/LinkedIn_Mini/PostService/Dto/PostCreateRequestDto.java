package com.Nishant.LinkedIn_Mini.PostService.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCreateRequestDto {
    //insert the validation
    @NotBlank(message = "Post or content is required")
    String content;
}
