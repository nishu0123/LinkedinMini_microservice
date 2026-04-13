package com.Nishant.LinkedIn_Mini.PostService.Util;

import com.Nishant.LinkedIn_Mini.PostService.Custom.CustomMultipartFile;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;

public class MultipartFileUtil {

    public static MultipartFile base64ToMultipart(String base64) {
        try {
            // Handle Data URI prefix if present
            String base64Data = base64.contains(",") ? base64.split(",")[1] : base64;

            byte[] decoded = Base64.getDecoder().decode(base64Data);

            return new CustomMultipartFile(
                    decoded,
                    "image.jpg",
                    MediaType.IMAGE_JPEG_VALUE
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert base64 to MultipartFile", e);
        }
    }
}