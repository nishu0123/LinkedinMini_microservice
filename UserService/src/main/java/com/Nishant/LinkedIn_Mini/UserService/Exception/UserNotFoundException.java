package com.Nishant.LinkedIn_Mini.UserService.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
