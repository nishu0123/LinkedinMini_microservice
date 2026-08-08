package com.Nishant.LinkedIn_Mini.UploaderService.Service;

import com.Nishant.LinkedIn_Mini.UploaderService.Constants.UploadProvider;
import com.Nishant.LinkedIn_Mini.UploaderService.Dto.CreatePostResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface UploadStrategy {

    UploadProvider getProvider();

    CreatePostResponseDto upload(MultipartFile file);
}
