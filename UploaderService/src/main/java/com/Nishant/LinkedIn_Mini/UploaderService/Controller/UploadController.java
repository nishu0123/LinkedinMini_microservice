package com.Nishant.LinkedIn_Mini.UploaderService.Controller;

import com.Nishant.LinkedIn_Mini.UploaderService.Service.UploaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.entity.mime.MultipartPart;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file) {
        log.info("Upload API hit, file name: {}", file.getOriginalFilename());
        String url = uploaderService.upload(file);
        return ResponseEntity.ok(url);
    }


    //To Do : implement the google-cloud  , image will be uploaded on google-cloud

}
