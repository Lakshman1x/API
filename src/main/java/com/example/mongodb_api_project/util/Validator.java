package com.example.mongodb_api_project.util;

import com.example.mongodb_api_project.exception_handler.ValidationException;
import org.springframework.validation.BindingResult;


public class Validator {
    private Validator() {
    }

    public static void validate(BindingResult bind) throws ValidationException {
        if (bind.hasErrors()) {
            throw new ValidationException(bind);
        }
    }

}

