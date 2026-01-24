package com.Nishant.LinkedIn_Mini.UploaderService.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.entity.mime.MultipartPart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CloudinaryUploaderService implements UploaderService {

//      ("request has been passed to the service layer ");
    private final Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile file) {
        log.info("request passed to the service layer");
        // Upload the image
        try {
            Map<String, Object> params = Map.of(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true
            );

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    params
            );

            String imageUrl = (String) uploadResult.get("secure_url");
            log.info("Image uploaded successfully: {}", imageUrl);

            return imageUrl;

        } catch (IOException e) {
            log.error("Error uploading image to Cloudinary", e);
            throw new RuntimeException("Image upload failed", e);
        }

//        System.out.println(
//                cloudinary.uploader().upload("https://cloudinary-devs.github.io/cld-docs-assets/assets/images/coffee_cup.jpg", params1));
    }
}
