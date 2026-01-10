package com.Nishant.LinkedIn_Mini.PostService.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PostDto {
    private Long id;
    private String content;
    private Long userId;
    private Date createdAt;
}
