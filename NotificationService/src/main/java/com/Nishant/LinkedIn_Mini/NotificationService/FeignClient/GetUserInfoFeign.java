package com.Nishant.LinkedIn_Mini.NotificationService.FeignClient;


import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.NotificationUserInfoDto;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "UserService" //this is the service name for which feign will look on discovery service and will feth the ip and port
        //to make the api call
)
public interface GetUserInfoFeign {

    @GetMapping("/user/auth/{userId}/getUserInfo")
    UserInfoDto GetUserInfo(@PathVariable Long userId);

    @PostMapping("/user/auth/userInfo/bulk")
    List<NotificationUserInfoDto> GetUserInfoInBulk(@RequestBody List<Long> userIdList);
}
