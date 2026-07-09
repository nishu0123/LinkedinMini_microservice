package com.Nishant.LinkedIn_Mini.PostService.Service;
import java.util.Base64;

import com.Nishant.LinkedIn_Mini.PostService.Custom.Base64ToMultipartFile;
import com.Nishant.LinkedIn_Mini.PostService.Dto.CreatePostUploaderResponseDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostCreateRequestDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDto;
import com.Nishant.LinkedIn_Mini.PostService.Entity.PostEntity;
import com.Nishant.LinkedIn_Mini.PostService.Exception.ImageUploadException;
import com.Nishant.LinkedIn_Mini.PostService.Exception.InvalidImageException;
import com.Nishant.LinkedIn_Mini.PostService.FeignClient.imageUploaderFeign;
import com.Nishant.LinkedIn_Mini.PostService.Repositroy.PostCreateRepository;
import com.Nishant.LinkedIn_Mini.PostService.Util.MultipartFileUtil;
import com.nishant.linkedinmini.common.contracts.ApiResponse;
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
        PostEntity postEntity = new PostEntity();
        Date now = new Date();//it will set the current data automatically
        postEntity.setCreatedAt(now);
        postEntity.setUserId(tempUserId);

        log.info("Creating post for userId={}", tempUserId);

        // Before setting the content get the content url, after uploading the content
        String base64Image = postRequestDto.getContent(); // image

// Create a variable to hold the clean data, defaulting to the raw string
        String base64Data = base64Image;

// 1. SAFELY Remove the header ONLY if a comma prefix exists
        if (base64Image != null && base64Image.contains(",")) {
            String[] parts = base64Image.split(",");
            if (parts.length > 1) {
                base64Data = parts[1];
            }
        } else {
            log.info("Processing direct raw Base64 string (no metadata prefix detected).");
        }

        // 2. Decode to byte array and also handle the exception
        byte[] decodedBytes;

        try {
            decodedBytes = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException e) {
            log.error("Failed to decode Base64 image", e);
            throw new InvalidImageException("Invalid Base64 image.");
        }

        // 3. Convert to our custom MultipartFile
        MultipartFile customMultipartFile = new Base64ToMultipartFile(decodedBytes, "upload.png");

        log.info("Uploading image to UploaderService");
        // 4. Call Feign Client
        ResponseEntity<ApiResponse<CreatePostUploaderResponseDto>> response =
                imageUploaderFeign.upload(customMultipartFile);

//        uploaderClient.uploadFile(customMultipartFile);
        ApiResponse<CreatePostUploaderResponseDto> apiResponse = response.getBody();
        if (apiResponse == null || apiResponse.getData() == null) {
            log.error("UploaderService returned an invalid response");
            throw new ImageUploadException("Image upload failed.");
        }

        String imageUrl = response.getBody().getData().getImgUrl();
        String publicId = response.getBody().getData().getPublicId();

        postEntity.setImgUrl(imageUrl); //store the string of the response that contains the url of the image
        postEntity.setPublicId(publicId);

        return postCreateRepository.save(postEntity);

    }

    public PostEntity getPost(Long postId) {

        //here we will fetch the data from the database with the help of the repository
        return postCreateRepository.getReferenceById(postId);

    }

    public List<PostDto> getAllPost(Long userId) {

        List<PostEntity> allPostEntity =  postCreateRepository.getAllPostByUserId(userId);

        //but here we have to do this with each object , so here we will use loop
        List<PostDto> allPostDto = new ArrayList<>();
        for(PostEntity postEntity : allPostEntity)
        {
            PostDto postDto =  modelMapper.map(postEntity , PostDto.class);
            allPostDto.add(postDto); //by mapping each object and then creating a list
        }
        return allPostDto;
    }
}
