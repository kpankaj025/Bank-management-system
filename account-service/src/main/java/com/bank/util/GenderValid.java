package com.bank.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValid implements ConstraintValidator<ValidGender, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.isEmpty() || value == null) {
            return false;
        }
        if (value.toLowerCase().equals("male") || value.toLowerCase().equals("female")
                || value.toLowerCase().equals("other")) {
            return true;
        }

        return false;
    }

}
