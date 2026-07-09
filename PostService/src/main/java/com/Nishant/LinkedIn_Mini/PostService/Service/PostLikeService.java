package com.Nishant.LinkedIn_Mini.PostService.Service;

import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDislikeRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostLikeDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostLikeRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Entity.PostLikeEntity;
import com.Nishant.LinkedIn_Mini.PostService.Exception.PostAlreadyLikedException;
import com.Nishant.LinkedIn_Mini.PostService.Exception.ResourceNotFoundException;
import com.Nishant.LinkedIn_Mini.PostService.Repositroy.PostLikeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PostLikeService {
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final PostLikeRepository postLikeRepository;

    public PostLikeDto addLike(PostLikeRequestDto postLikeRequestDto , Long userId) {
        PostLikeEntity postLikeEntity = new PostLikeEntity();
        postLikeEntity.setPostId(postLikeRequestDto.getPostId());
        postLikeEntity.setUserId(userId);

        //first check if likes already has been recorded
        if(true == postLikeRepository.existsByPost_IdAndUserId(postLikeRequestDto.getPostId(),userId) )
        {
            log.warn("post id = {} already liked by userid = {}" , postLikeRequestDto.getPostId() , userId);
            throw new PostAlreadyLikedException(
                    "User has already liked this post"
            );
        }else{
            PostLikeEntity savedPostLike =  postLikeRepository.save(postLikeEntity); //saving the data into the database
            return modelMapper.map(savedPostLike , PostLikeDto.class);
        }
    }

    //TO DO : implement in again it seems to be wrong
    public void deleteLike(PostDislikeRequestDto postDislikeRequestDto, Long tempUserId) throws AccessDeniedException {

        //here we have to update the query , first of all fetch the all post_id of the user
        //and then delete that postId
        List<PostLikeEntity> listOfPostOfUser = postLikeRepository.findByUserId(tempUserId);

        //now loop through these list of the post and find the desired post that to be deleted
        boolean flagPostDeleted = false;
        for(PostLikeEntity value : listOfPostOfUser)
        {
            if(value.getPostId().equals(postDislikeRequestDto.getPostId()))
            {
                postLikeRepository.delete(value);
                flagPostDeleted = true;
                break;
            }
        }

        if(flagPostDeleted == false)
        {
            log.error("post not found ");
        }
    }
}
