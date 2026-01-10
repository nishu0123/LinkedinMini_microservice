package com.Nishant.LinkedIn_Mini.PostService.Controller;

import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDislikeRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostLikeDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostLikeRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/likes")
public class PostLikeController {
    //add the required api
    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public PostLikeService postLikeService;


    //this is also working
    @GetMapping("/greet")
    public String greet()
    {
        return "welcome to likes";
    }

//this is also working
    @PostMapping("/addLike")
    public ResponseEntity<PostLikeDto> addLike(@RequestBody PostLikeRequestDto postLikeRequestDto)
    {
        //now we have to save this like
        Long tempUserId = 1L; //this is the temporary user id
        PostLikeDto postLikeDto = postLikeService.addLike(postLikeRequestDto , tempUserId);
        return new ResponseEntity<>(postLikeDto , HttpStatus.ACCEPTED); //constructor
        /*
        //another approach
        return ResponseEntity
        .status(HttpStatus.ACCEPTED)
        .body(postLikeDto);
         */
    }


    //this is now working
    @DeleteMapping("/deleteLike")
    public ResponseEntity<HttpStatus> deleteLike(@RequestBody PostDislikeRequestDto postDislikeRequestDto)
    {
        //now we have to dislike this post
        Long tempUserId = 1L;//this is the temporary user id
        try {
            postLikeService.deleteLike(postDislikeRequestDto , tempUserId);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

}
