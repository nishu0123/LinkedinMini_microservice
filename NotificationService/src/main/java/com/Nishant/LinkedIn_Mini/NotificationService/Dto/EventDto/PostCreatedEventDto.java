package com.Nishant.LinkedIn_Mini.PostService.Dto.EventDto;


import lombok.Data;

@Data
public class PostCreatedEventDto {
    private Long userId; //this will be used to fetch the connection of this user
    String imageUrl; //this will used as information for email and the push-notification
}
