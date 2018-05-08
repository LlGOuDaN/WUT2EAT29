package com.example.l8411.wut2eat29.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by l8411 on 4/14/2018.
 */

public class UserProfile implements Parcelable {
    private String UserID;
    private String UserNickName;
    private List<String> Top3Choice;
    private List<String> history;
    private List<String> vote;

    public UserProfile() {

    }

    public UserProfile(String userID) {
        UserID = userID;
        UserNickName = "Default";
        this.Top3Choice = new ArrayList<String>();
        this.history = new ArrayList<String>();
        this.vote = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            Top3Choice.add("N/A");
        }
    }


    protected UserProfile(Parcel in) {
        UserID = in.readString();
        Top3Choice = in.createStringArrayList();
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

    public String getUserNiceName() {
        return UserNickName;
    }

    public void setUserNiceName(String userNiceName) {
        UserNickName = userNiceName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public List<String> getTop3Choice() {
        return Top3Choice;
    }

    public void setTop3Choice(List<String> top3Choice) {
        Top3Choice = top3Choice;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public List<String> getVote() {
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
        parcel.writeString(UserID);
        parcel.writeStringList(Top3Choice);
        parcel.writeStringList(history);
        parcel.writeStringList(vote);
    }
}
