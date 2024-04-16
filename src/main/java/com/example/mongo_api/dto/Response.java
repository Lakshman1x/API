package com.example.mongo_api.dto;

import jakarta.validation.constraints.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Response {
    private boolean status;
    private String message;
    private String dateTimeStamp;

    public Response(boolean status, String message){
        this();
        this.status=status;
        if(!Objects.isNull(message)){
            this.message=message;
        }

    }

    public Response(){
        this.message="";
        setDateTimeStamp();}

    public void setStatus(boolean status){this.status=status;}
    public  void setMessage(String message){this.message=message;}

    public void appendMessage(String msg){
//        if(this.message==null){this.message="";}
        this.message=this.message+msg+", ";
    }


    private void setDateTimeStamp(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        this.dateTimeStamp= sdf.format(date);
    }

    public String getDateTimeStamp(){return this.dateTimeStamp;}

    public String getMessage() {
        return this.message;
    }
    public  boolean getStatus(){return  this.status;}

}
