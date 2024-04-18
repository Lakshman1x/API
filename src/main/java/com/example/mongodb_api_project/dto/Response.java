package com.example.mongodb_api_project.dto;
import org.springframework.http.HttpStatus;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Response {
    private HttpStatus status;
    private String message;
    private String dateTimeStamp;

    public Response(HttpStatus status, String message) {
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

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
