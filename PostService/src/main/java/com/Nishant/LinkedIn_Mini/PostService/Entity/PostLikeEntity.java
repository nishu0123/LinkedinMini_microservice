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
    private Long id;



    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;



    @JoinColumn(
            name = "post_id",
            nullable = false
    )
    private Long postId;



    @ManyToOne(fetch = FetchType.LAZY)
    @org.hibernate.annotations.OnDelete(
            action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    private PostEntity post;

}
