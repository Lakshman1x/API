package com.example.mongo_api.dto;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Response {
    private int status;
    private String message;
    private String dateTimeStamp;

    public Response(int status, String message) {
        this();
        this.status = status;
        if (!Objects.isNull(message)) {
            this.message = message;
        }

    }

    public Response() {
        this.message = "";
        setDateTimeStamp();
    }

    public void appendMessage(String msg) {
        this.message = this.message + msg + ", ";
    }

    private void setDateTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        this.dateTimeStamp = sdf.format(date);
    }

    public String getDateTimeStamp() {
        return this.dateTimeStamp;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
