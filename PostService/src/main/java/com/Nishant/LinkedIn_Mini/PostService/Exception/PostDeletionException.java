package com.Nishant.LinkedIn_Mini.PostService.Exception;

public class PostDeletionException extends RuntimeException {

    public PostDeletionException(String message) {
        super(message);
    }

    public PostDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
