package com.example.internfinallyt2.exception;

import com.example.internfinallyt2.exception.customValidation.CategoryInUseException;
import com.example.internfinallyt2.exception.customValidation.CustomIOException;
import com.example.internfinallyt2.exception.customValidation.DuplicateCourseCodeException;
import com.example.internfinallyt2.exception.customValidation.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    //trim
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorCode = ((FieldError) error).getCode();

            // Kiểm tra nếu lỗi là typeMismatch (ví dụ không chuyển được từ String sang Long[])
            if (errorCode.equals("typeMismatch")) {
                String rejectedValue = ((FieldError) error).getRejectedValue().toString();
                String errorMessage = messageSource.getMessage("error.notmatch", new Object[]{fieldName, rejectedValue}, LocaleContextHolder.getLocale());
                errors.put(fieldName, errorMessage);
            } else if (errorCode.equals("NotNull") && fieldName.equals("categoryIds")) { // Điều chỉnh theo tên field của bạn
                String errorMessage = messageSource.getMessage("error.category.invalid", null, LocaleContextHolder.getLocale());
                errors.put(fieldName, errorMessage);
            } else {
                String errorMessage = messageSource.getMessage(error.getCode(), error.getArguments(), error.getDefaultMessage(), LocaleContextHolder.getLocale());
                errors.put(fieldName, errorMessage);
            }
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(ResourceNotFoundException ex) {
        Locale locale = LocaleContextHolder.getLocale();

        String errorMessage = messageSource.getMessage("error.notfound", new Object[]{ex.getResourceName(), ex.getResourceId()}, locale);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DuplicateCourseCodeException.class})
    public ResponseEntity<String> handleDuplicateException(DuplicateCourseCodeException ex) {
        Locale locale = LocaleContextHolder.getLocale();

        String errorMessage = messageSource.getMessage("error.duplicate", new Object[]{ex.getResourceName(), ex.getResourceCode()}, locale);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({CategoryInUseException.class})
    public ResponseEntity<String> handleCategoryInUseException(CategoryInUseException ex) {
        Locale locale = LocaleContextHolder.getLocale();

        String errorMessage = messageSource.getMessage("error.inuse", new Object[]{ex.getResourceName(), ex.getResourceCode()}, locale);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.notmatch", new Object[]{ex.getName(),ex.getName()}, locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomIOException.class})
    public ResponseEntity<String> handleCustomIOException(CustomIOException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.file", new Object[]{}, locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


}
