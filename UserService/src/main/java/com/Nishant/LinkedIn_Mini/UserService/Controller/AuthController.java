package com.Nishant.LinkedIn_Mini.UserService.Controller;

import com.Nishant.LinkedIn_Mini.UserService.Dto.RefreshTokenRequestDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.RefreshTokenResponseDto;
import com.Nishant.LinkedIn_Mini.UserService.Service.AuthService;
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
    public ResponseEntity<RefreshTokenResponseDto> refreshAcessToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto)
    {
        //call the service that will refresh the token
        String accessToken =  authService.refreshAcessToken(refreshTokenRequestDto);


        RefreshTokenResponseDto response = new RefreshTokenResponseDto();
        response.setAccessToken(accessToken);
        //return the response
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }



    //api for refresh token
    /*
    //here need to add the Dto for RequestTokenRequest and other dependency

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {

        if (!jwtService.isTokenValid(request.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        Long userId = jwtService.extractUserId(request.getRefreshToken());
        UserEntity user = userRepository.findById(userId).orElseThrow();

        String newAccessToken = jwtService.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );

        return new AuthResponse(newAccessToken, request.getRefreshToken());
    }
    */

}
