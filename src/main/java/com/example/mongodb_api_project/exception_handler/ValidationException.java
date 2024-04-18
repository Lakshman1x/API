package com.example.mongodb_api_project.exception_handler;

import org.springframework.validation.BindingResult;


public class ValidationException extends Exception {
    private final transient BindingResult bindingResult;

    public ValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

}
