package com.Nishant.LinkedIn_Mini.UploaderService.Service;

import com.Nishant.LinkedIn_Mini.UploaderService.Constants.UploadProvider;
import com.Nishant.LinkedIn_Mini.UploaderService.Dto.CreatePostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleCloudUploadStrategy implements UploadStrategy{
    @Override
    public UploadProvider getProvider() {
        return UploadProvider.GOOGLE_CLOUD;
    }

    @Override
    public CreatePostResponseDto upload(MultipartFile file) {

        log.info("Google-Cloud strategy executed");

        return null;
    }
}
