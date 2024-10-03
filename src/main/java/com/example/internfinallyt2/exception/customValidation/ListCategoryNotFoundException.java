package com.example.internfinallyt2.exception.customValidation;

public class ListCategoryNotFoundException extends RuntimeException {
    private String resourceName;
    private Object resourceId;

    public ListCategoryNotFoundException(String resourceName, Object resourceId) {
        super(String.format("%s not found with id : '%s'", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Object getResourceId() {
        return resourceId;
    }
}
