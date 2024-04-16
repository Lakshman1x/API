package com.example.mongo_api.util;

import com.example.mongo_api.dto.Response;
import com.example.mongo_api.exception_handler.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;


public class Validator {
    private static final Logger logger = LogManager.getLogger(Validator.class);
    private Validator(){}
    public static Response validate(BindingResult bind) throws ValidationException {
        Response res = new Response();
        if(bind.hasErrors()){
        throw new ValidationException(bind);
        }else{
            res.setStatus(true);
        }
        return res;
    }

}

