package com.Nishant.LinkedIn_Mini.PostService.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCrated {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
}
