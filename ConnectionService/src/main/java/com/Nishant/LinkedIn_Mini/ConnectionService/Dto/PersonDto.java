package com.Nishant.LinkedIn_Mini.ConnectionService.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Getter
@Setter
//@RequiredArgsConstructor
public class PersonDto {
//    private Long id;
    private Long userId;
    private String userName;
}
