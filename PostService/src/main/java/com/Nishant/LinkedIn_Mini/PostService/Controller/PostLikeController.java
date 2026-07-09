package com.Nishant.LinkedIn_Mini.PostService.Controller;

import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDislikeRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostLikeDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostLikeRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Service.PostLikeService;
import com.Nishant.LinkedIn_Mini.PostService.Util.ResponseBuilder;
import com.nishant.linkedinmini.common.contracts.ApiResponse;
import jakarta.validation.Valid;
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
    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public PostLikeService postLikeService;

    @GetMapping("/greet")
    public String greet()
    {
        return "welcome to likes";
    }

    @PostMapping("/addLike")
    public ResponseEntity<ApiResponse<PostLikeDto>> addLike(@Valid  @RequestBody PostLikeRequestDto postLikeRequestDto , @RequestHeader("X-User-Id") Long userId)
    {
        PostLikeDto postLikeDto = postLikeService.addLike(postLikeRequestDto , userId);
//        return new ResponseEntity<>(postLikeDto , HttpStatus.ACCEPTED);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "Post Liked successfully",
                        postLikeDto
                ));


    }

    @DeleteMapping("/deleteLike")
    public ResponseEntity<ApiResponse<HttpStatus>> deleteLike(@Valid @RequestBody PostDislikeRequestDto postDislikeRequestDto , @RequestHeader("X-User-Id") Long userId)
    {

        try {
            postLikeService.deleteLike(postDislikeRequestDto , userId);
        } catch (AccessDeniedException e) {
            //check it
            log.error("post-dislike operation was not successful " + e);
            throw new RuntimeException(e);
        }
//        return ResponseEntity.ok(HttpStatus.ACCEPTED);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "Post Disliked successfully",
                        null
                ));
    }

}
