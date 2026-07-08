package com.Nishant.LinkedIn_Mini.UserService.FeignClient;


import com.nishant.linkedinmini.common.contracts.ApiResponse;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//here i have to add the library for the feign
@FeignClient(
        name = "ConnectionService"
//        configuration = FeignMultipartConfig.class // CRITICAL: Point to your config
//        url = "http://localhost:8083" // OR Eureka name
)

public interface UserFeign {

    @PostMapping("/connection/people/addUserNode")
    public ResponseEntity<ApiResponse<PersonDto>> addUserNode(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody PersonDto personDto);
    //example implement the similar
    // 2. Image Delete Endpoint
//    @DeleteMapping("/upload/file/delete")
//    ResponseEntity<DeleteImageResponseDto> deleteImage(@RequestBody DeleteImageRequestDto request);
}
