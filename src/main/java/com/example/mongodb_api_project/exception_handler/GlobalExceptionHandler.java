package com.example.mongodb_api_project.exception_handler;

import com.example.mongodb_api_project.dto.Response;
import com.mongodb.MongoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MongoAPIException.class)
    public ResponseEntity<Response> handleMongoAPIException(MongoAPIException ex) {
        Response response = new Response(ex.getStatusCode(), ex.getMessage());
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Response> handleValidationException(ValidationException ex) {
        Response response = new Response(HttpStatus.BAD_REQUEST, "Validation error : ");
        logger.error("Person Entity validation failed");
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            response.appendMessage(err.getDefaultMessage());
            logger.warn(err.getDefaultMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<Response> handleMongoException(MongoException ex) {
        Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, "Some error occurred. Please contact support");
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception ex) {
        Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
