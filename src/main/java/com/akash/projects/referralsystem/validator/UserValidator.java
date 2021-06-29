package com.akash.projects.referralsystem.validator;

import com.akash.projects.referralsystem.domain.User;
import com.akash.projects.referralsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        // check for existing user
        checkUsername(user, errors);
        // add password checks
        checkPassword(user, errors);
        // check if referralCode is valid
        checkSignupReferralCode(user, errors);
    }

    private void checkUsername(User user, Errors errors) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser!=null) {
            errors.rejectValue("username", "already present", "Username already exists");
        }
    }

    private void checkPassword(User user, Errors errors) {
        if (user.getPassword().length()<6) {
            errors.rejectValue("password", "length", "Password length must be atleast of 6 characters");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("password", "confirmPassword", "Both passwords should match");
        }
    }

    private void checkSignupReferralCode(User user, Errors errors) {
        if (Objects.nonNull(user.getSignupReferralCode())) {
            User existingUser = userRepository.findByUserReferralCode(user.getSignupReferralCode());
            if (existingUser == null) {
                errors.rejectValue("signupReferralCode", "invalid", "signupReferralCode is invalid");
            }
        }
    }
}
