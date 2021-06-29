package com.akash.projects.referralsystem.controller;

import com.akash.projects.referralsystem.domain.User;
import com.akash.projects.referralsystem.payload.request.LoginRequest;
import com.akash.projects.referralsystem.payload.response.JwtLoginSuccessResponse;
import com.akash.projects.referralsystem.security.JwtTokenProvider;
import com.akash.projects.referralsystem.service.MapValidationErrorService;
import com.akash.projects.referralsystem.service.UserService;
import com.akash.projects.referralsystem.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.akash.projects.referralsystem.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        userValidator. validate(user, result);
        ResponseEntity<?> errorsToResponse = mapValidationErrorService.mapErrorsToResponse(result);
        if (errorsToResponse!=null) {
            return errorsToResponse;
        }
        User newUser = userService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                              BindingResult result) throws Exception {
        ResponseEntity<?> errorsToResponse = mapValidationErrorService.mapErrorsToResponse(result);
        if (errorsToResponse!=null) {
            return errorsToResponse;
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
            return new ResponseEntity<JwtLoginSuccessResponse>(new JwtLoginSuccessResponse(true, jwt), HttpStatus.OK);
        }
        catch (DisabledException e) {
            throw new Exception("User is disabled", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("Credentials are invalid", e);
        }
    }
}
