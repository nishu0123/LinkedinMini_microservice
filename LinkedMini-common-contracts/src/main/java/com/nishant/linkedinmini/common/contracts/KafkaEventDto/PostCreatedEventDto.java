package com.nishant.linkedinmini.common.contracts.KafkaEventDto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostCreatedEventDto {
    private Long userId; //this will be used to fetch the connection of this user
    String imageUrl; //this will used as information for email and the push-notification
    String email;
    String userName;
}