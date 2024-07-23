package com.lld4.userauthenticationservice.service;

import com.lld4.userauthenticationservice.models.User;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;


public interface IAuthService {
    public User resister(String email, String password);
    public Pair<User, MultiValueMap<String, String>> login(String email, String password);
}
