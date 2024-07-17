package com.lld4.userauthenticationservice.controllers;

import com.lld4.userauthenticationservice.dtos.LoginRequestDTO;
import com.lld4.userauthenticationservice.dtos.SignupRequestDTO;
import com.lld4.userauthenticationservice.dtos.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
   Controller to support services :- Signup ,   Login , ForgetPassword, Logout
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signup")
    public UserDTO signUp(@RequestBody SignupRequestDTO signupRequestDTO) {


        return null;
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return null;
    }

}
