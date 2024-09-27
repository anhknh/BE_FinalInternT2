package com.example.internfinallyt2.exception.customValidation;

import java.io.IOException;

public class CustomIOException extends RuntimeException {
    public CustomIOException(IOException cause) {
        super(cause);
    }
}
