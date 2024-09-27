package com.example.internfinallyt2.exception.customValidation.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositiveLongArrayValidator implements ConstraintValidator<PositiveLongArray, Long[]> {
    @Override
    public boolean isValid(Long[] value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        for (Long id : value) {
            if (id == null || id <= 0) {
                return false;
            }
        }
        return true;
    }
}
