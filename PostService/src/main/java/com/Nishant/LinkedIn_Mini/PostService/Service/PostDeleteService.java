package com.Nishant.LinkedIn_Mini.PostService.Service;

import com.Nishant.LinkedIn_Mini.PostService.Dto.DeleteImageResponseDto;
import com.Nishant.LinkedIn_Mini.PostService.Dto.PostDto;
import com.Nishant.LinkedIn_Mini.PostService.Entity.PostEntity;
import com.Nishant.LinkedIn_Mini.PostService.Exception.PostDeletionException;
import com.Nishant.LinkedIn_Mini.PostService.Exception.PostNotFoundException;
import com.Nishant.LinkedIn_Mini.PostService.FeignClient.imageUploaderFeign;
import com.Nishant.LinkedIn_Mini.PostService.Repositroy.PostDeleteRepository;
import com.nishant.linkedinmini.common.contracts.ApiResponse;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.DeleteImageRequestDto;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class PostDeleteService {
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final PostDeleteRepository postDeleteRepository;

    @Autowired
    private final imageUploaderFeign imageUploaderFeign;

    @Transactional
    public PostDto deletePost(String publicId) {

        PostEntity postEntity = postDeleteRepository.findByPublicId(publicId);
        if(postEntity == null){
            log.warn("Post not found. publicId={}", publicId);
            throw new PostNotFoundException("Post not found");
        }

        // THE SAFETY SHIELD: Stop execution if the public_id column is blank
        if (postEntity.getPublicId() == null || postEntity.getPublicId().trim().isEmpty()) {
            log.error("Post {} has no Cloudinary publicId. Cannot delete image.", postEntity.getId());
            throw new PostDeletionException("Cannot delete cloud assets: This post does not have a valid Cloudinary public ID reference.");
        }

        // Now it is 100% safe to assemble your request and call Feign/Repository
        DeleteImageRequestDto request = new DeleteImageRequestDto();
        request.setPublicId(postEntity.getPublicId());

        log.info("Requesting image deletion from PostService. publicId={}", publicId);

        ResponseEntity<ApiResponse<DeleteImageResponseDto>> response = imageUploaderFeign.deleteImage(request);

        log.info("Cloudinary delete status={}", response.getBody().getData().getStatus());

        if (response.getStatusCode() == HttpStatus.OK && "SUCCESS".equals(response.getBody().getData().getStatus())) {

            log.info("Post deleted successfully. postId={}, publicId={}",
                    postEntity.getId(),
                    publicId);
            postDeleteRepository.delete(postEntity);

        }else{
            log.error("Post deletion failed. publicId={}, reason={}",
                    publicId,
                    response.getBody().getMessage());
            throw new PostDeletionException(
                    response.getBody().getMessage()
            );
        }
        return modelMapper.map(postEntity , PostDto.class);
    }
}
