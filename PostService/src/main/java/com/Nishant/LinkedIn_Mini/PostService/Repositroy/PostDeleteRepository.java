package com.Nishant.LinkedIn_Mini.PostService.Repositroy;

import com.Nishant.LinkedIn_Mini.PostService.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface PostDeleteRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByPublicId(String publicId);
//    void delete(Optional<PostEntity> postEntity);
}
