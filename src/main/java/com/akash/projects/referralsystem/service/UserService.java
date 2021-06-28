package com.akash.projects.referralsystem.service;

import com.akash.projects.referralsystem.domain.User;
import com.akash.projects.referralsystem.exceptions.user.UsernameAlreadyExistsException;
import com.akash.projects.referralsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        try {
            user.setUsername(user.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setConfirmPassword("");
            return userRepository.save(user);
        }
        catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username: '" + user.getUsername() + "' already exists");
        }
    }
}
