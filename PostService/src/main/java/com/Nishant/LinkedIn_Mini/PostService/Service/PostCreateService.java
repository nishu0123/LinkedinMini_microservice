package com.Nishant.LinkedIn_Mini.PostService.Service;

import com.Nishant.LinkedIn_Mini.PostService.Dto.PostCreateRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDto;
import com.Nishant.LinkedIn_Mini.PostService.Entity.PostEntity;
import com.Nishant.LinkedIn_Mini.PostService.Repositroy.PostCreateRepository;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.log;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class PostCreateService {
    @Autowired
    private final PostCreateRepository postCreateRepository;
    private final ModelMapper modelMapper;
    public PostEntity savePostIntoDb(PostCreateRequestDto postRequestDto, Long tempUserId) {
        //here i have to save the post into the database and then return the saved data to the user
//        PostEntity postEntity = modelMapper.map(postRequestDto , PostEntity.class);
        PostEntity postEntity = new PostEntity();
        Date now = new Date();//it will set the current data automatically
        postEntity.setCreatedAt(now);
        postEntity.setUserId(tempUserId);
        log.info("postEntity data = {} " , postEntity);//print the post entity value
        postEntity.setContent(postRequestDto.getContent());
//        PostEntity savedPost = postCreateRepository.save(postEntity);
//        return savedPost;
        //upper two commnted line can be written as
        return postCreateRepository.save(postEntity);
    }

    public PostEntity getPost(Long postId) {
        //here we will fetch the data from the database with the help of the repository
        PostEntity postEntity = postCreateRepository.getReferenceById(postId);
        return postEntity;
    }

    public List<PostDto> getAllPost(Long userId) {
//        List<PostEntity> allPostEntity =  postCreateRepository.getAllPostByUserId(userId);
        List<PostEntity> allPostEntity =  postCreateRepository.getAllPostByUserId(userId);
        //but here we have to do this with each object , so here we will use loop
        List<PostDto> allPostDto = new ArrayList<>();
        for(PostEntity postEntity : allPostEntity)
        {
            PostDto postDto =  modelMapper.map(postEntity , PostDto.class);
            allPostDto.add(postDto); //by mapping each object and then creating a list
        }
//        List<PostDto> allPostDto = modelMapper.map(allPostEntity , PostDto.class);
        return allPostDto;
    }
}
