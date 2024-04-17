package com.example.mongo_api.util;

import com.example.mongo_api.exception_handler.ValidationException;
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

