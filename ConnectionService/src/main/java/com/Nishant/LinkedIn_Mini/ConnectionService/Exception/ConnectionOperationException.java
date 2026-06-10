package com.Nishant.LinkedIn_Mini.ConnectionService.Exception;



public class ConnectionOperationException extends RuntimeException {

    public ConnectionOperationException(String message) {
        super(message);
    }

    public ConnectionOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}