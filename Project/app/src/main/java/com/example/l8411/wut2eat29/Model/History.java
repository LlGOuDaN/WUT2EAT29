package com.example.l8411.wut2eat29.Model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;

public class History {
    private Restaurant resturant;
    private Date date;
    private String dateFormated;

    public History() {
    }

    public History(Restaurant resturant, Date date) {
        this.resturant = resturant;
        this.date = date;
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        dateFormated = sdf.format(date);
    }

    public Restaurant getResturant() {
        return resturant;
    }

    public void setResturant(Restaurant resturant) {
        this.resturant = resturant;
    }
    @Exclude
    public Date getDate() {
        return date;
    }
    @Exclude
    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateFormated() {
        return dateFormated;
    }

    public void setDateFormated(String dateFormated) {
        this.dateFormated = dateFormated;
    }

}
