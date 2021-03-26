package com.example.mycard.Model;

import java.util.Date;

public class Request {

    int Request_ID;
    int Type_ID;
    int Student_ID;
    Date date;
    int Status;


    public Request() {
    }

    public int getRequest_ID() {
        return Request_ID;
    }

    public void setRequest_ID(int request_ID) {
        Request_ID = request_ID;
    }

    public int getType_ID() {
        return Type_ID;
    }

    public void setType_ID(int type_ID) {
        Type_ID = type_ID;
    }

    public int getStudent_ID() {
        return Student_ID;
    }

    public void setStudent_ID(int student_ID) {
        Student_ID = student_ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
