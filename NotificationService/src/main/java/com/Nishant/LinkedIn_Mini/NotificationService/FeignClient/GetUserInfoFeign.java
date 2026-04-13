package com.Nishant.LinkedIn_Mini.NotificationService.FeignClient;


import com.Nishant.LinkedIn_Mini.NotificationService.Dto.FeignDto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "UserService" //this is the service name for which feign will look on discovery service and will feth the ip and port
        //to make the api call
)
public interface GetUserInfoFeign {
    @GetMapping("/user/auth/{userId}/getUserInfo")
    UserInfoDto GetUserInfo(@PathVariable Long userId , @RequestHeader("X-USER-ID") Long xUserId);
}
