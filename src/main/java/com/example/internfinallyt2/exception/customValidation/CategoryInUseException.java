package com.example.internfinallyt2.exception.customValidation;

public class CategoryInUseException extends RuntimeException {
    private final String resourceName;
    private final Object resourceCode;

    public CategoryInUseException(String resourceName, Object resourceCode) {
        super(String.format("Cannot delete category '%s' with code: '%s'. It is currently in use.", resourceName, resourceCode));
        this.resourceName = resourceName;
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Object getResourceCode() {
        return resourceCode;
    }
}

