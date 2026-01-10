package com.Nishant.LinkedIn_Mini.PostService.Repositroy;

import com.Nishant.LinkedIn_Mini.PostService.Entity.PostEntity;
import com.Nishant.LinkedIn_Mini.PostService.Service.PostCreateService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCreateRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> getAllPostByUserId(Long userId);
}
