package com.Nishant.LinkedIn_Mini.UserService.Service;

import com.Nishant.LinkedIn_Mini.UserService.Dto.UserInfoDto;
import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import com.Nishant.LinkedIn_Mini.UserService.Exception.InvalidCredentialsException;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.RefreshTokenRepository;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.UserRepository;
import com.nishant.linkedinmini.common.contracts.FeignDto.NotificationUserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;


//    @Autowired
//    private final RefreshTokenRepository refreshTokenRepository;
//
    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public UserInfoDto GetUserInfo(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        //map the userEntity to userInfoDto
        return modelMapper.map(userEntity , UserInfoDto.class);
    }

    public void logout(Long userId) {
        log.info("/logout api reached the service layer userId = {}" , userId);
        try
        {
//            userRepository.deleteById(userId);
//            String token = refreshTokenRepository.findById()
            refreshTokenRepository.deleteById(userId);

        }catch (Exception e){
            log.error("Failed to DELETE refresh token for");


            throw new InvalidCredentialsException(
                    "Unable to logout something went wrong");
        }

        log.info("/logout api deleted refresh token");

    }

    public List<NotificationUserInfoDto> getUserInfoInBulk(List<Long> userIdList)
    {
        List<UserEntity> userEntityList =
                userRepository.findAllById(userIdList);

        return userEntityList.stream()
                .map(user -> modelMapper.map(
                        user,
                        NotificationUserInfoDto.class
                ))
                .toList();
    }
}
