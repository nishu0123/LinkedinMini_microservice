package com.Nishant.LinkedIn_Mini.PostService.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@Slf4j
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    //image can not be present , it possible that only video is uploaded
//    @Column(name = "image_url")
//    private  String imageUrl;

    //video feature is yet to implement
//    @Column(name = "video_url")
//    private String videoUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}
