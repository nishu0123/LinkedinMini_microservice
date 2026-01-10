package com.Nishant.LinkedIn_Mini.PostService.Controller;

import com.Nishant.LinkedIn_Mini.PostService.Auth.AuthContextHolder;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostCreateRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDto;
import com.Nishant.LinkedIn_Mini.PostService.Entity.PostEntity;
import com.Nishant.LinkedIn_Mini.PostService.Service.PostCreateService;
import lombok.NoArgsConstructor;
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


    @GetMapping("/greet")
    public String greetFromPostService()
    {
        return "hello world from post service ";
    }
    /*
    implement the post mapping  , which will create the post and will return the post
    we will get the request from the user to create the post , in which only post related info
    will be there we will create the post id and will fetch the user id from api header
    or any other method we can apply (like using api-gateway we can inject userid in the header
    this part will be handled later at this time i will hardcore the user id
    */

    //this is working
    @PostMapping("/createPost")
    public ResponseEntity<PostDto> CreatePost(@RequestBody PostCreateRequestDto postRequestDto  , @RequestHeader ("X-User-Id")Long userId)
    {
        //here i have to create the post and then return the post

        //here we have to save the data into the database
        log.info("received user id in PostService from the Request Header  = " + userId);
//        Long tempUserId = 1L; //here using the temporary id , later this will be integrated with api-gateway
//        Long tempUserId = userId; //here using the temporary id ,now updating the userId
        //now with the help of the AuthContextHolder , we can get the userId anywhere in the service
        Long tempUserId = AuthContextHolder.getCurrentUserId(); //get the id using interceptor

        log.info("received user id in PostService from the Interceptor  = " + tempUserId);
        PostEntity postEntity = postCreateService.savePostIntoDb(postRequestDto , tempUserId);
        PostDto postDto = modelMapper.map(postEntity , PostDto.class); //mapping the postEntity data with PostDto
        /*
        now return the response to the user
        update post created
        when post is created in future we will use Kafka to publish this message to the broker
        and then other service like notification can consume it to notify user about the
        postCreation
        */
//         space for further implementation

        /*

        implement the logic here
         */

        //return the response
        return new ResponseEntity<>(postDto , HttpStatus.CREATED);
    }


    //this is also working
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId)
    {
        //here we will get the post id in the request done by the user
        //and the we will return the post having that id
        PostEntity postEntity = postCreateService.getPost(postId);
        PostDto postDto = modelMapper.map(postEntity , PostDto.class);
        if(postDto.getContent() == null)
        {
            return new ResponseEntity<>(postDto , HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postDto , HttpStatus.ACCEPTED);
//        return ResponseEntity.ok(postDto); //return the post be fetching the data from the database
    }

    //below is also working
    @GetMapping("/user/{userId}/allPost")
    public ResponseEntity<List<PostDto>> getAllPost(@PathVariable Long userId)
    {
        List<PostDto> allPost= postCreateService.getAllPost(userId);
        return ResponseEntity.ok(allPost);
    }


}
