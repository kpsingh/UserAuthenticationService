package com.lld4.userauthenticationservice.dtos;

import com.lld4.userauthenticationservice.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String email;
    private Role role;

}
