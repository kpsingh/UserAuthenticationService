package com.lld4.userauthenticationservice.service;

import com.lld4.userauthenticationservice.models.User;
import com.lld4.userauthenticationservice.respository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private UserRepo userRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User resister(String email, String password) {

        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            return null; // user already exist;
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepo.save(newUser);
    }

    @Override
    public User login(String email, String password) {
        return null;
    }
}
