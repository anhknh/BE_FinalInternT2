package com.example.internfinallyt2.exception.customValidation.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File must be in JPG or PNG format")
                    .addConstraintViolation();
            return false;
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size must be less than 2MB")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
