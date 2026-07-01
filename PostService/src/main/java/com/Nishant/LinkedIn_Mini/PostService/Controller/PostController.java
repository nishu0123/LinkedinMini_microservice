package com.Nishant.LinkedIn_Mini.PostService.Controller;

import com.Nishant.LinkedIn_Mini.PostService.Auth.AuthContextHolder;
import com.Nishant.LinkedIn_Mini.PostService.Dto.*;
import com.Nishant.LinkedIn_Mini.PostService.Entity.PostEntity;
import com.Nishant.LinkedIn_Mini.PostService.Service.PostCreateService;
import com.Nishant.LinkedIn_Mini.PostService.Service.PostCreatedEventProducer;
import com.Nishant.LinkedIn_Mini.PostService.Service.PostDeleteService;
import com.nishant.linkedinmini.common.contracts.KafkaEventDto.PostCreatedEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@ResponseBody
@RequiredArgsConstructor
//@RestController = @Controler + @Responsebody
@RequestMapping("/core")
public class PostController {
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final PostCreateService postCreateService;

    @Autowired
    private final PostCreatedEventProducer postCreatedEventProducer;

    @Autowired
    private  final PostDeleteService postDeleteService;

    @GetMapping("/greet")
    public String greetFromPostService()
    {
        return "hello world from post service ";
    }
    //this is working
    @PostMapping("/createPost")
    public ResponseEntity<PostDto> CreatePost(@RequestBody PostCreateRequestDto postRequestDto  , @RequestHeader ("X-User-Id")Long userId)
    {
        log.info("received user id in PostService from the Request Header  = " + userId);
//        Long tempUserId = userId; //here using the temporary id ,now updating the userId
        //now with the help of the AuthContextHolder , we can get the userId anywhere in the service
        Long interceptorUserId = AuthContextHolder.getCurrentUserId(); //get the id using interceptor

        log.info("userId fetched from the header : " + userId + " userId fetched using interceptor : " + interceptorUserId);

        PostEntity postEntity = postCreateService.savePostIntoDb(postRequestDto , interceptorUserId);
        log.info("Post saved into the database successfully!");
        PostDto postDto = modelMapper.map(postEntity , PostDto.class); //mapping the postEntity data with PostDto

        //now imageUrl have been saved into the database
        //produce the event -> post-created

        String imageUrl = postDto.getImgUrl();

        //producing event when post is created
        PostCreatedEventDto postCreatedEventDto = new PostCreatedEventDto();
        postCreatedEventDto.setImageUrl(imageUrl);
        postCreatedEventDto.setUserId(interceptorUserId);
        //check if there is requirement of the userName and email then we need to call the feign for that information
//        postCreatedEventDto.setEmail();
        postCreatedEventProducer.sendPostEvent(postCreatedEventDto);
        log.info("sending postCreateEvent Dto");

        return new ResponseEntity<>(postDto , HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId)
    {
        PostEntity postEntity = postCreateService.getPost(postId);
        PostDto postDto = modelMapper.map(postEntity , PostDto.class);
        if(postDto.getImgUrl() == null)
        {
            return new ResponseEntity<>(postDto , HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postDto , HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/{userId}/allPost")
    public ResponseEntity<List<PostDto>> getAllPost(@PathVariable Long userId)
    {
        List<PostDto> allPost= postCreateService.getAllPost(userId);
        return ResponseEntity.ok(allPost);
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<PostDto> deletePost(@RequestBody DeleteImageRequestDto deleteImageRequestDto){
        //get the post_id from request body
        String publicId = deleteImageRequestDto.getPublicId();

        //at first get the public id using the post id
        PostDto postDto = postDeleteService.deletePost(publicId);

        return new ResponseEntity<>(postDto , HttpStatus.ACCEPTED);
    }
}
