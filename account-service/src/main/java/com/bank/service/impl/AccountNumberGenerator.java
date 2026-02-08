package com.bank.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class AccountNumberGenerator {

    public String generateAccountNumber() {

        // generate 10 digit random string
        Random random = new Random();

        // First digit: 1–9 (no leading zero)
        int firstDigit = random.nextInt(9) + 1;

        // Remaining 9 digits
        long remainingDigits = (long) (random.nextDouble() * 1_000_000_000L);

        return firstDigit + String.format("%09d", remainingDigits);

    }
}
