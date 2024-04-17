package com.example.mongo_api.exception_handler;

public class MongoAPIException extends Exception {

    private final int statusCode;

    public MongoAPIException(int statusCode,String message){
        super(message);
        this.statusCode=statusCode;
    }

    public  int getStatusCode(){return this.statusCode;}

}
