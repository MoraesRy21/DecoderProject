package com.ead.authuser.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameConstraintValidationImpl implements ConstraintValidator<UsernameContraintValidation, String> {
    @Override
    public void initialize(UsernameContraintValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(username.equals(null) || username.trim().isEmpty() || username.contains(" "))
            return false;
        return true;
    }
}
