package com.Nishant.LinkedIn_Mini.PostService.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Getter
@Setter
@Table(name = "post_like")
public class PostLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(
            name = "post_id",
            nullable = false
    )
    Long postId;

    @Column(
            name = "user_id",
            nullable = false
    )
    Long userId;
}
