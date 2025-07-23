package com.devcodeops.java_backend.dtos;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String mail;
}