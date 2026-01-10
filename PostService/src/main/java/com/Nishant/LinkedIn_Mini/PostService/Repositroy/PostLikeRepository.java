package com.Nishant.LinkedIn_Mini.PostService.Repositroy;

import com.Nishant.LinkedIn_Mini.PostService.Entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostLikeRepository extends JpaRepository<PostLikeEntity , Long> {
    PostLikeEntity findByPostId(Long postId);

    List<PostLikeEntity> findByUserId(Long UserId);
}
