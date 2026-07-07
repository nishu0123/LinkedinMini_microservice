package com.nishant.linkedinmini.common.contracts.Dto.FeignDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class NotificationUserInfoDto {
    @NotNull(message = "userId is required")
    @Positive(message = "user id must be a positive number")
    private Long userId;

    @NotBlank(message = "user name is required")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
