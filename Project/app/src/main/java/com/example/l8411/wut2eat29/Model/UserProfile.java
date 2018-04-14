package com.example.l8411.wut2eat29.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by l8411 on 4/14/2018.
 */

public class UserProfile implements Parcelable {
    private int UserID;
    private String[] Top3Choice;
    private ArrayList<String> history;
    private ArrayList<String> vote;

    public UserProfile(int userID, String[] top3Choice, ArrayList<String> history, ArrayList<String> vote) {
        UserID = userID;
        Top3Choice = top3Choice;
        this.history = history;
        this.vote = vote;
    }

    protected UserProfile(Parcel in) {
        UserID = in.readInt();
        Top3Choice = in.createStringArray();
        history = in.createStringArrayList();
        vote = in.createStringArrayList();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String[] getTop3Choice() {
        return Top3Choice;
    }

    public void setTop3Choice(String[] top3Choice) {
        Top3Choice = top3Choice;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public ArrayList<String> getVote() {
        return vote;
    }

    public void setVote(ArrayList<String> vote) {
        this.vote = vote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(UserID);
        parcel.writeStringArray(Top3Choice);
        parcel.writeStringList(history);
        parcel.writeStringList(vote);
    }
}
