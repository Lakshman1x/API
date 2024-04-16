package com.example.mongo_api.exception_handler;

import org.springframework.validation.BindingResult;

public class ValidationException extends Exception{
    private BindingResult bindingResult;
    public ValidationException(BindingResult bindingResult){
        this.bindingResult=bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

}
