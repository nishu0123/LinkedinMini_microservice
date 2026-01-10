package com.Nishant.LinkedIn_Mini.PostService.Dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Getter
@Setter
public class PostLikeDto {
    Long id;
    Long postId;
    Long userId;
}
