package com.Nishant.LinkedIn_Mini.UserService.Service;

import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import com.Nishant.LinkedIn_Mini.UserService.Exception.UserNotFoundException;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.RefreshTokenRepository;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.UserRepository;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.NotificationUserInfoDto;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.UserInfoDto;
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
        //check if user data is avaialble in the database or not
        if(userEntity.isEmpty()){
            log.info("user not found - userId = {} " , userId);
            throw new UserNotFoundException("user with user id : "  + userId + " does not exist");
        }
        //map the userEntity to userInfoDto
        return modelMapper.map(userEntity , UserInfoDto.class);
    }

    public void logout(Long userId) {
        log.info("/logout api reached the service layer userId = {}" , userId);
        //try catch block should not be used in the database operation let database give
        //the error information
        refreshTokenRepository.deleteById(userId);

        log.info("refresh token deleted");

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
