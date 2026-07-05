package com.Nishant.LinkedIn_Mini.UploaderService.Service;

import com.Nishant.LinkedIn_Mini.UploaderService.Dto.CreatePostResponseDto;
import com.Nishant.LinkedIn_Mini.UploaderService.Dto.DeleteImageResponseDto;
import com.Nishant.LinkedIn_Mini.UploaderService.Exception.CloudinaryDeleteException;
import com.Nishant.LinkedIn_Mini.UploaderService.Exception.ImageUploadException;
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

    private final Cloudinary cloudinary;

    @Override
    public CreatePostResponseDto upload(MultipartFile file) {

        CreatePostResponseDto createPostResponseDto = new CreatePostResponseDto();
        log.info("request passed to the service layer");
        // Upload the image
        try {
            Map<String, Object> params = Map.of(
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", false
            );

            Map<String, Object>  uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    params
            );

            String imageUrl = (String) uploadResult.get("secure_url");
            String publicId = (String) uploadResult.get("public_id");
            log.info("Image uploaded successfully: {}", imageUrl);

            createPostResponseDto.setImgUrl(imageUrl);
            createPostResponseDto.setPublicId(publicId);

            return createPostResponseDto;

        } catch (IOException e) {
            log.error(
                    "Failed to upload image '{}' to Cloudinary",
                    file.getOriginalFilename(),
                    e
            );

            throw new ImageUploadException(
                    "Failed to upload image",
                    e
            );
        }

    }


    @Override
    public DeleteImageResponseDto deletePost(String publicId) {
        DeleteImageResponseDto deleteImageResponseDto = new DeleteImageResponseDto();

        try {
            Map result = cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.emptyMap()
            );

            String cloudinaryResult =
                    (String) result.get("result");

            log.info("status of deleting post from cloudinary service : " + cloudinaryResult);

            if ("ok".equals(cloudinaryResult)) {

                deleteImageResponseDto.setStatus("SUCCESS");
                deleteImageResponseDto.setMessage("Image deleted successfully");
                return deleteImageResponseDto;
            }

            throw new CloudinaryDeleteException(
                    "Cloudinary returned: "
                            + cloudinaryResult
            );

        } catch (IOException e) {

            throw new CloudinaryDeleteException(
                    "Failed to communicate with Cloudinary",
                    e
            );
        }
    }

}
