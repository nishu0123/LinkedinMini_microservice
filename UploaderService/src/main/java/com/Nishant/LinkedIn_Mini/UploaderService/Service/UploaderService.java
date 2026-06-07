package com.Nishant.LinkedIn_Mini.UploaderService.Service;

import com.Nishant.LinkedIn_Mini.UploaderService.Dto.CreatePostResponseDto;
import com.Nishant.LinkedIn_Mini.UploaderService.Dto.DeleteImageResponseDto;
import org.apache.hc.client5.http.entity.mime.MultipartPart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploaderService {

    CreatePostResponseDto upload(MultipartFile file);

    DeleteImageResponseDto deletePost(String publicId);
}
