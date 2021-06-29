package com.akash.projects.referralsystem.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyEnrolledInReferralSystemException extends RuntimeException{

    public UserAlreadyEnrolledInReferralSystemException(String message) {
        super(message);
    }
}
