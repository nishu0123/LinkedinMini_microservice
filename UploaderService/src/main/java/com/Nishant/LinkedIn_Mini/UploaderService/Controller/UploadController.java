package com.Nishant.LinkedIn_Mini.UploaderService.Controller;

import com.Nishant.LinkedIn_Mini.UploaderService.Dto.CreatePostResponseDto;
import com.Nishant.LinkedIn_Mini.UploaderService.Dto.DeleteImageResponseDto;
import com.Nishant.LinkedIn_Mini.UploaderService.Service.UploaderService;
import com.Nishant.LinkedIn_Mini.UploaderService.Util.ResponseBuilder;
import com.cloudinary.Api;
import com.nishant.linkedinmini.common.contracts.ApiResponse;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.DeleteImageRequestDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/file")
public class UploadController {

    @Autowired
    private final UploaderService uploaderService;

    public UploadController(UploaderService uploaderService) {
        this.uploaderService = uploaderService;
    }


    @GetMapping("/greet")
    public String greetFromUserService()
    {
        return "hello world from the uploader service ";
    }

    @PostMapping(value = "/uploadImage" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CreatePostResponseDto>> upload(@RequestPart("file") MultipartFile file) {
        log.info("Upload API hit, file name: {}", file.getOriginalFilename());
//        String url = uploaderService.upload(file);
        CreatePostResponseDto createPostResponseDto = uploaderService.upload(file);

//        return ResponseEntity.ok(createPostResponseDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "image uploaded to the cloudinary successfully",
                        createPostResponseDto
                ));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<DeleteImageResponseDto>> deleteImage(
           @Valid @RequestBody DeleteImageRequestDto request
    ) {
        DeleteImageResponseDto deleteImageResponseDto =  uploaderService.deletePost(
                request.getPublicId()
        );

        //we will reach to this statement if image/post will be deleted successfully
        //other it will throw exception and will not reach at this point
//        return new ResponseEntity<>(deleteImageResponseDto , HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "Post deleted successfully",
                        deleteImageResponseDto
                ));
    }


    //To Do : implement the google-cloud  , image will be uploaded on google-cloud

}
