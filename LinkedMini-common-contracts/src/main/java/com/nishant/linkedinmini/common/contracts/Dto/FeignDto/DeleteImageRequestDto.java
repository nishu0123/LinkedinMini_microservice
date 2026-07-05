package com.nishant.linkedinmini.common.contracts.Dto.FeignDto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DeleteImageRequestDto {

    @NotBlank(message = "publicId is required")
    private String publicId;
}
