package com.Nishant.LinkedIn_Mini.ConnectionService.Exception;



public class DuplicateUserNameException extends RuntimeException {

    public DuplicateUserNameException(String message) {
        super(message);
    }

    public DuplicateUserNameException(String message, Throwable cause) {
        super(message, cause);
    }
}