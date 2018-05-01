package com.example.l8411.wut2eat29.Model;

import java.util.Date;

public class History {
    private String resturantName;
    private Date date;

    public History() {
    }

    public History(String resturantName, Date date) {
        this.resturantName = resturantName;
        this.date = date;
    }

    public String getResturantName() {
        return resturantName;
    }

    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
