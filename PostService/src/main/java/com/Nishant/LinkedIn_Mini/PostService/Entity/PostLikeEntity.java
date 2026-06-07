package com.Nishant.LinkedIn_Mini.PostService.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Getter
@Setter
//condition to make the unique of the combined property of post_id and user_id , as we know
//only one like is possible on a post by a user so duplicate entry in the database will not
//violate the data consistency
@Table(
        name = "post_like",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "post_id",
                                "user_id"
                        }
                )
        }
)
public class PostLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "post_id",
            nullable = false
    )
    private Long postId;

    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "post_id",
            nullable = false,
            insertable = false, // <-- FIXES THE AMBIGUITY CRASH
            updatable = false  // <-- FIXES THE AMBIGUITY CRASH
    )
    @org.hibernate.annotations.OnDelete(
            action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    private PostEntity post;
}