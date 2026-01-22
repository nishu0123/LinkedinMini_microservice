package com.Nishant.LinkedIn_Mini.UploaderService.Service;

import org.apache.hc.client5.http.entity.mime.MultipartPart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploaderService {

    String upload(MultipartFile file);
}
