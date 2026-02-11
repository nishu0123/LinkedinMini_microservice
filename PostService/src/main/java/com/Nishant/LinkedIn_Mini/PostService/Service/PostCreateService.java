package com.Nishant.LinkedIn_Mini.PostService.Service;
import java.util.Base64;

import com.Nishant.LinkedIn_Mini.PostService.Custom.Base64ToMultipartFile;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostCreateRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDto;
import com.Nishant.LinkedIn_Mini.PostService.Entity.PostEntity;
import com.Nishant.LinkedIn_Mini.PostService.FeignClient.imageUploaderFeign;
import com.Nishant.LinkedIn_Mini.PostService.Repositroy.PostCreateRepository;
import com.Nishant.LinkedIn_Mini.PostService.Util.MultipartFileUtil;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private final imageUploaderFeign imageUploaderFeign;

    private final ModelMapper modelMapper;
    public PostEntity savePostIntoDb(PostCreateRequestDto postRequestDto, Long tempUserId) {
        //here i have to save the post into the database and then return the saved data to the user
//        PostEntity postEntity = modelMapper.map(postRequestDto , PostEntity.class);
        PostEntity postEntity = new PostEntity();
        Date now = new Date();//it will set the current data automatically
        postEntity.setCreatedAt(now);
        postEntity.setUserId(tempUserId);
        log.info("postEntity data = {} " , postEntity);//print the post entity value

        //Before setting the content get the content url , after uploading the content
        String base64Image = postRequestDto.getContent();//image

        // 1. Remove the header (e.g., "data:image/png;base64,")
        String base64Data = base64Image.split(",")[1];

        // 2. Decode to byte array
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

        // 3. Convert to our custom MultipartFile
        MultipartFile customMultipartFile = new Base64ToMultipartFile(decodedBytes, "upload.png");

        // 4. Call Feign Client
        ResponseEntity<String> response =
                imageUploaderFeign.upload(customMultipartFile);

//        uploaderClient.uploadFile(customMultipartFile);

        String imageUrl = response.getBody();

        /*
        String base64Image = postRequestDto.getContent();//image
        MultipartFile multipartFile =
                MultipartFileUtil.base64ToMultipart(base64Image);

        ResponseEntity<String> response =
                imageUploaderFeign.upload(multipartFile);

        String imageUrl = response.getBody();

         */



//        String response = imageUploaderFeign.upload(postRequestDto);
        postEntity.setContent(imageUrl); //store the string of the response that contains the url of the image

//        PostEntity savedPost = postCreateRepository.save(postEntity);
//        return savedPost;
        //upper two commented line can be written as
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
