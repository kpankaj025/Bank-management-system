package com.bank.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.entity.Account;
import com.bank.exception.ResourceNotFoundException;
import com.bank.payload.UserResponse;
import com.bank.repository.AccountRepo;

@Service
public class AuthService {

    private AccountRepo accountRepo;
    private PasswordEncoder passwordEncoder;

    public AuthService(AccountRepo accountRepo, PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse authenticate(String email, String password) {
        Account acc = accountRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("invalid email"));
        if (!passwordEncoder.matches(password, acc.getPassword())) {

            throw new ResourceNotFoundException("password not match");

        }
        UserResponse response = new UserResponse();
        response.setId(acc.getId());
        response.setEmail(acc.getEmail());
        response.setRole(acc.getRole());

        return response;
    }

}
