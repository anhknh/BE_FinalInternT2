package com.example.internfinallyt2.exception.customValidation;

public class DuplicateCourseCodeException extends RuntimeException {
    private String resourceName;
    private Object resourceCode;

    public String getResourceName() {
        return resourceName;
    }

    public Object getResourceCode() {
        return resourceCode;
    }

    public DuplicateCourseCodeException(String resourceName, Object resourceCode) {
        super(String.format("Duplicate %s found with code: '%s'", resourceName, resourceCode));
        this.resourceName = resourceName;
        this.resourceCode = resourceCode;
    }
}
