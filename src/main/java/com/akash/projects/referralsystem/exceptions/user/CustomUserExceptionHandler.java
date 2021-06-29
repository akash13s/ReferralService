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
        CustomUserExceptionResponse usernameAlreadyExistsResponse = new CustomUserExceptionResponse
                (usernameAlreadyExistsException.getMessage());
        return new ResponseEntity<Object>(usernameAlreadyExistsException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserAlreadyEnrolledInReferralSystem(UserAlreadyEnrolledInReferralSystemException
            usernameAlreadyExistsException, WebRequest request) {
        CustomUserExceptionResponse userAlreadyEnrolledInReferralSystemResponse =
                new CustomUserExceptionResponse(usernameAlreadyExistsException.getMessage());
        return new ResponseEntity<Object>(userAlreadyEnrolledInReferralSystemResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNotEnrolledInReferralSystem(UserNotEnrolledInReferralSystemException
                                                                                          usernameAlreadyExistsException,
                                                                              WebRequest request) {
        CustomUserExceptionResponse userNotEnrolledInReferralSystemResponse =
                new CustomUserExceptionResponse(usernameAlreadyExistsException.getMessage());
        return new ResponseEntity<Object>(userNotEnrolledInReferralSystemResponse, HttpStatus.BAD_REQUEST);
    }
}
