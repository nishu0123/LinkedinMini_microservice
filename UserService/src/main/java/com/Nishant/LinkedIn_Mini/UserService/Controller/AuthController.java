package com.Nishant.LinkedIn_Mini.UserService.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
@RequestMapping("/token")
public class AuthController {



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
