package com.example.LinkMongo.Services;

import com.example.LinkMongo.Model.Dto.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class Validator {
    private static final Logger logger = LogManager.getLogger(com.example.LinkMongo.Services.Validator.class);
    public static Response getResponse(BindingResult bind){
        Response res= new Response();
        if(bind.hasErrors()){
            for(FieldError err : bind.getFieldErrors()){
                logger.warn(err.getDefaultMessage());
                res.setStatus(false);
                res.setMessage("Invalid user input");
            }
        }else{
            logger.info("user input is valid");
            res.setStatus(true);
        }

        return res;
    }
    private Validator(){}
}

