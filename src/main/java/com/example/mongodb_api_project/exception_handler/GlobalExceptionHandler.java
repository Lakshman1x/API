package com.example.mongodb_api_project.exception_handler;

import com.example.mongodb_api_project.dto.Response;
import com.mongodb.MongoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MongoAPIException.class)
    public ResponseEntity<Response> handlePersonNotInDbException(MongoAPIException ex) {
        Response response = new Response(ex.getStatusCode(), ex.getMessage());
        logger.warn(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Response> handleValidationException(ValidationException ex) {
        Response response = new Response(HttpStatus.BAD_REQUEST, "Validation error : ");
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            response.appendMessage(err.getDefaultMessage());
            logger.warn(err.getDefaultMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<Response> handleMongoException(MongoException ex) {

        Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, "MongoDB error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception ex) {
        Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}