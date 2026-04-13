package com.Nishant.LinkedIn_Mini.NotificationService.FeignClient;


import com.Nishant.LinkedIn_Mini.NotificationService.Dto.FeignDto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
        name = "ConnectionService"
)
public interface GetFollowerFeign {

    @GetMapping("/connection/people/{userId}/first-degree")
    List<PersonDto> getFirstDegreeConnection(
            @PathVariable("userId") Long userId,
            @RequestHeader("X-USER-ID") String xUserId // Add this
    );

    /*
    @GetMapping("/connection/people/{userId}/second-degree")
    public List<PersonDto> getSecondDegreeConnection(@PathVariable Long userId);
    */

/*
    @GetMapping("/connection/people/{userId}/third-degree")
    public List<PersonDto> getThirdDegreeConnection(@PathVariable Long userId);

 */

}
