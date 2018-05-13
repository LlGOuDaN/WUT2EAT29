package com.example.l8411.wut2eat29.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Vote {

    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashMap<String, Integer> getVoteDetails() {
        return voteDetails;
    }

    public void setVoteDetails(HashMap<String, Integer> voteDetails) {
        this.voteDetails = voteDetails;
    }

    private String voteName;
    private Date date;
    private HashMap<String,Integer> voteDetails;

    public Vote(){

    }

    public Vote(String voteName, Date date, HashMap<String, Integer> voteDetails) {
        this.voteName = voteName;
        this.date = date;
        this.voteDetails = voteDetails;
    }
}
