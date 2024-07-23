package com.lld4.userauthenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> userAlreadyExistsExceptionResponseEntity(UserAlreadyExistsException ex) {
        CustomErrorResponse error = new CustomErrorResponse("USER_ALREADY_EXISTS", ex.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> userAlreadyExistsExceptionResponseEntity(UsernameNotFoundException ex) {
        CustomErrorResponse error = new CustomErrorResponse("INVALID_CREDENTIALS", ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
