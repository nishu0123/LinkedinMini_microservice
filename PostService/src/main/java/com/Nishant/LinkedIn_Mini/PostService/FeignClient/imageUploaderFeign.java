package com.Nishant.LinkedIn_Mini.PostService.FeignClient;

import com.Nishant.LinkedIn_Mini.PostService.Config.FeignMultipartConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
    ResponseEntity<String> upload(@RequestPart("file") MultipartFile file);
}
