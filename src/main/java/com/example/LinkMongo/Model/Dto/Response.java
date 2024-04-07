package com.example.LinkMongo.Model.Dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Response {
    private boolean status;
    private String message;
    private String dateTimeStamp;

    public Response(boolean status,String message){
        this.status=status;
        this.message=message;
        setDateTimeStamp();
    }

    public Response(){setDateTimeStamp();}
    public void setStatus(boolean status){this.status=status;}
    public  void setMessage(String message){this.message=message;}

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
