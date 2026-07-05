package com.nishant.linkedinmini.common.contracts.Dto.FeignDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "userId is required")
    private Long userId;

    @NotBlank(message = "user name is required")
    private String userName;
}
