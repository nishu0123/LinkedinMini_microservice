package com.nishant.linkedinmini.common.contracts.FeignDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Data
@Getter
@Setter
//@RequiredArgsConstructor
public class PersonDto {
//    private Long id;
    private Long userId;
    private String userName;
}
