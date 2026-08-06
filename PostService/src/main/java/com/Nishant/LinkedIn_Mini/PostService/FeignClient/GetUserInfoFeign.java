package com.Nishant.LinkedIn_Mini.PostService.FeignClient;


import com.nishant.linkedinmini.common.contracts.ApiResponse;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.NotificationUserInfoDto;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "UserService" //this is the service name for which feign will look on discovery service and will feth the ip and port
        //to make the api call
)
public interface GetUserInfoFeign {

    @GetMapping("/user/auth/{userId}/getUserInfo")
    public ResponseEntity<ApiResponse<UserInfoDto>> GetUserInfo(@PathVariable Long userId);

    @PostMapping("/user/auth/userInfo/bulk")
    public ResponseEntity<ApiResponse<List<NotificationUserInfoDto>>> GetUserInfoInBulk(@RequestBody List<Long> userIdList);
}
