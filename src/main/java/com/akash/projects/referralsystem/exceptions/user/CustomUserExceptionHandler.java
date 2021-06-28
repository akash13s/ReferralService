package com.akash.projects.referralsystem.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class CustomUserExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException usernameAlreadyExistsException,
                                                                    WebRequest request) {
        UsernameAlreadyExistsResponse usernameAlreadyExistsResponse = new UsernameAlreadyExistsResponse
                (usernameAlreadyExistsException.getMessage());
        return new ResponseEntity<Object>(usernameAlreadyExistsException, HttpStatus.BAD_REQUEST);
    }
}
