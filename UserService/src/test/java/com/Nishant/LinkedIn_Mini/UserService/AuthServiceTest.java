package com.Nishant.LinkedIn_Mini.UserService;

import com.Nishant.LinkedIn_Mini.UserService.Dto.SignInRequestDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.UserDto;
import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import com.Nishant.LinkedIn_Mini.UserService.FeignClient.UserFeign;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.RefreshTokenRepository;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.UserRepository;
import com.Nishant.LinkedIn_Mini.UserService.Service.AuthService;
import com.Nishant.LinkedIn_Mini.UserService.Service.ConnectionNodeService;
import com.Nishant.LinkedIn_Mini.UserService.Service.JwtService;
import com.Nishant.LinkedIn_Mini.UserService.Service.ValidationService;
import com.Nishant.LinkedIn_Mini.UserService.Util.BCrypt;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.PersonDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.Nishant.LinkedIn_Mini.UserService.Exception.DuplicateUserNameException;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserFeign userFeign;

    @Mock
    private ConnectionNodeService connectionNodeService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BCrypt bCrypt;

    @Mock
    private JwtService jwtService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldCreateUserSuccessfully() {

        SignInRequestDto dto = new SignInRequestDto();

        dto.setUserName("Nishant");
        dto.setEmail("nishu@gmail.com");
        dto.setPassword("password123");


        when(bCrypt.hash("password123"))
                .thenReturn("encryptedPassword");


        UserEntity savedUser = new UserEntity();

        savedUser.setId(1L);
        savedUser.setUserName("Nishant");
        savedUser.setEmail("nishu@gmail.com");
        savedUser.setPassword("encryptedPassword");

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedUser);


        UserDto userDto = new UserDto();

        userDto.setId(1L);
        userDto.setUserName("Nishant");
        userDto.setEmail("nishu@gmail.com");

        when(modelMapper.map(any(UserEntity.class), eq(UserDto.class)))
                .thenReturn(userDto);

        UserDto result = authService.signUp(dto);

        assertNotNull(result);

        assertEquals("Nishant", result.getUserName());

        assertEquals("nishu@gmail.com", result.getEmail());

        assertEquals(1L, result.getId());


        verify(validationService)
                .validateDuplicateUserByEmail(dto.getEmail());


        verify(userRepository, times(1))
                .save(any(UserEntity.class));


        verify(connectionNodeService)
                .createNode(anyLong(), any(PersonDto.class));


//        verifyNoMoreInteractions(connectionNodeService);

        verify(bCrypt).hash("password123");


    }

    @Test
    void shouldThrowDuplicateUserExceptionWhenEmailAlreadyExists() {

        // Arrange
        SignInRequestDto dto = new SignInRequestDto();
        dto.setUserName("Nishant");
        dto.setEmail("nishu@gmail.com");
        dto.setPassword("password123");

        doThrow(new DuplicateUserNameException("User already exists"))
                .when(validationService)
                .validateDuplicateUserByEmail(dto.getEmail());

        // Act & Assert
        DuplicateUserNameException exception = assertThrows(
                DuplicateUserNameException.class,
                () -> authService.signUp(dto)
        );

        assertEquals("User already exists", exception.getMessage());

        // Verify interactions
        verify(validationService)
                .validateDuplicateUserByEmail(dto.getEmail());

        // Since validation failed, nothing after it should execute
        verify(userRepository, never())
                .save(any(UserEntity.class));

        verify(connectionNodeService, never())
                .createNode(anyLong(), any(PersonDto.class));

        verify(modelMapper, never())
                .map(any(UserEntity.class), eq(UserDto.class));
    }

}
