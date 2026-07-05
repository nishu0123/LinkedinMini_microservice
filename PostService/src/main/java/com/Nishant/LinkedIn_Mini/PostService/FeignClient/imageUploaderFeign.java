package com.Nishant.LinkedIn_Mini.PostService.FeignClient;

import com.Nishant.LinkedIn_Mini.PostService.Config.FeignMultipartConfig;
import com.Nishant.LinkedIn_Mini.PostService.Dto.CreatePostUploaderResponseDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.DeleteImageResponseDto;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.DeleteImageRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "UploaderService",
        configuration = FeignMultipartConfig.class // CRITICAL: Point to your config
//        url = "http://localhost:8083" // OR Eureka name
)

public interface imageUploaderFeign {
//    @PostMapping("/file")
// ADD THIS: consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    @PostMapping(value = "/upload/file/uploadImage", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<CreatePostUploaderResponseDto> upload(@RequestPart("file") MultipartFile file);

    // 2. Image Delete Endpoint
    @DeleteMapping("/upload/file/delete")
    ResponseEntity<DeleteImageResponseDto> deleteImage(@RequestBody DeleteImageRequestDto request);
}
