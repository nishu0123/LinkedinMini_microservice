package com.Nishant.LinkedIn_Mini.PostService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a requested Post cannot be found in the database.
 * Annotated with NOT_FOUND so Spring automatically returns an HTTP 404 status.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

    // Standard serial version UID for serializable classes
    private static final long serialVersionUID = 1L;

    public PostNotFoundException(String message) {
        super(message);
    }
}