package com.bank.payload;

import com.bank.entity.enums.UserRole;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String email;
    private UserRole role;
}
