package com.ChairShop.security.validation;

import com.ChairShop.model.request.user.RegistrationUserRequest;
import com.ChairShop.utils.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validator;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegistrationUserRequest> {

    @Override
    public boolean isValid(RegistrationUserRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return (request.getPassword().equals(request.getConfirmPassword()));
    }
}
