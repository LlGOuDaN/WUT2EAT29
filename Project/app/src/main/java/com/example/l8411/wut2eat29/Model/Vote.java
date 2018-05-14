package com.example.l8411.wut2eat29.Model;

import com.example.l8411.wut2eat29.Utils.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Vote {
    private String voteID;
    private String dateFormated;

    public  Vote(){

    }
    public Vote(String resturant){
        this.resturant = resturant;
        voteDetails = new HashMap<String, Integer>();
        Date date = Calendar.getInstance().getTime();
        dateFormated = utils.parseDate(date);
    }
    public String getVoteID() {
        return voteID;
    }

    public void setVoteID(String voteID) {
        this.voteID = voteID;
    }

    public String getDateFormated() {
        return dateFormated;
    }

    public void setDateFormated(String dateFormated) {
        this.dateFormated = dateFormated;
    }

    public String getResturant() {
        return resturant;
    }

    public void setResturant(String resturant) {
        this.resturant = resturant;
    }

    private String resturant;
    private HashMap<String,Integer> voteDetails;


    public HashMap<String, Integer> getVoteDetails() {
        return voteDetails;
    }

    public void setVoteDetails(HashMap<String, Integer> voteDetails) {
        this.voteDetails = voteDetails;
    }

}
