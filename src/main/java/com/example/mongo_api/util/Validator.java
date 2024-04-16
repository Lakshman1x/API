package com.example.mongo_api.util;

import com.example.mongo_api.dto.Response;
import com.example.mongo_api.exception_handler.ValidationException;
import org.springframework.validation.BindingResult;


public class Validator {
    private Validator() {
    }

    public static Response validate(BindingResult bind) throws ValidationException {
        Response res = new Response();
        if (bind.hasErrors()) {
            throw new ValidationException(bind);
        } else {
            res.setStatus(true);
        }
        return res;
    }

}

