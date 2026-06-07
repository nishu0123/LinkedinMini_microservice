package com.Nishant.LinkedIn_Mini.PostService.Exception;

public class PostAlreadyLikedException extends RuntimeException {

    public PostAlreadyLikedException(String message) {
        super(message);
    }
}