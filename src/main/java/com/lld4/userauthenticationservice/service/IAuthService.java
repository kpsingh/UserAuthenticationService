package com.lld4.userauthenticationservice.service;

import com.lld4.userauthenticationservice.models.User;


public interface IAuthService {
    public User resister(String email, String password);
    public User login(String email, String password);
}
