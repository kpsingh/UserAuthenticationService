package com.lld4.userauthenticationservice.controllers;

import com.lld4.userauthenticationservice.dtos.LoginRequestDTO;
import com.lld4.userauthenticationservice.dtos.SignupRequestDTO;
import com.lld4.userauthenticationservice.dtos.UserDto;
import com.lld4.userauthenticationservice.exceptions.UserAlreadyExistsException;
import com.lld4.userauthenticationservice.models.User;
import com.lld4.userauthenticationservice.service.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.MultiValueMap;
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

    private IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
        User user = authService.resister(signupRequestDTO.getEmail(), signupRequestDTO.getPassword());
        if (user == null) {
            throw new UserAlreadyExistsException("This email id has been already registered. Please try out with different email");
        }
        return new ResponseEntity<>(from(user), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Pair<User, MultiValueMap<String, String>> userWithHeaders = authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        if (userWithHeaders == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        User user = userWithHeaders.a;
        return new ResponseEntity<>(from(user), userWithHeaders.b, HttpStatus.OK);
    }

    private UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRole());
        return userDto;
    }

}
