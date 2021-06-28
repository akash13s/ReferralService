package com.akash.projects.referralsystem.validator;

import com.akash.projects.referralsystem.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (user.getPassword().length()<6) {
            errors.rejectValue("password", "length", "Password length must be atleast of 6 characters");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("password", "confirmPassword", "Both passwords should match");
        }
    }
}
