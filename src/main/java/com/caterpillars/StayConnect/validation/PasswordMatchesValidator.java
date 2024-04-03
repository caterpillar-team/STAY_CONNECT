package com.caterpillars.StayConnect.validation;

import com.caterpillars.StayConnect.dto.UserSignUpDto;
import com.caterpillars.StayConnect.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserSignUpDto user = (UserSignUpDto) value;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
