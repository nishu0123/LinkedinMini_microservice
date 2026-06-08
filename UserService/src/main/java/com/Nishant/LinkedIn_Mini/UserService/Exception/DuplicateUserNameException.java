package com.Nishant.LinkedIn_Mini.UserService.Exception;

public class DuplicateUserNameException extends RuntimeException{
    public DuplicateUserNameException(String message) {
        super(message);
    }
}
