package com.example.internfinallyt2.exception.customValidation.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PositiveLongArrayValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveLongArray {

    String message() default "All category IDs must be positive numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
