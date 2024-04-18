package com.example.mongodb_api_project.exception_handler;

import org.springframework.http.HttpStatus;

public class MongoAPIException extends Exception {

    private final HttpStatus statusCode;

    public MongoAPIException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

}
