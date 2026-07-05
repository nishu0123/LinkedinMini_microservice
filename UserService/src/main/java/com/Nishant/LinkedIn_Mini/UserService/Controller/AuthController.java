package com.Nishant.LinkedIn_Mini.UserService.Controller;

import com.Nishant.LinkedIn_Mini.UserService.Dto.RefreshTokenRequestDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.RefreshTokenResponseDto;
import com.Nishant.LinkedIn_Mini.UserService.Service.AuthService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
@RequestMapping("/token")
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/refreshAcessToken")
    public ResponseEntity<RefreshTokenResponseDto> refreshAcessToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto)
    {
        //call the service that will refresh the token
        String accessToken =  authService.refreshAcessToken(refreshTokenRequestDto);

        RefreshTokenResponseDto response = new RefreshTokenResponseDto();
        response.setAccessToken(accessToken);
        //return the response
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

}
