package com.example.l8411.wut2eat29.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by l8411 on 4/14/2018.
 */

public class UserProfile implements Parcelable {
    private String UserID;
    private String UserNickName;
    private List<String> Top3Choice;
    private List<History> history;
    private List<String> vote;
    private String avatarUrl;
    private History todayChoice;
    private String messageToken;

    public UserProfile() {

    }


    public UserProfile(String userID) {
        UserID = userID;
        UserNickName = "Default";
        avatarUrl = "https://firebasestorage.googleapis.com/v0/b/wut2eat29.appspot.com/o/default-avatar.png?alt=media&token=6293fd9b-07a2-470a-83c6-47b57cf17d89";
        messageToken = FirebaseInstanceId.getInstance().getToken();
        this.Top3Choice = new ArrayList<String>();
        this.history = new ArrayList<History>();
        this.vote = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            Top3Choice.add("N/A");
        }

    }


    protected UserProfile(Parcel in) {
        UserID = in.readString();
        Top3Choice = in.createStringArrayList();
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

    public String getUserNickName() {
        return UserNickName;
    }

    public void setUserNickName(String userNiceName) {
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

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }

    public List<String> getVote() {
        return vote;
    }

    public void setVote(ArrayList<String> vote) {
        this.vote = vote;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public History getTodayChoice() {
        return todayChoice;
    }

    public void setTodayChoice(History todayChoice) {
        this.todayChoice = todayChoice;
    }

    public String getMessageToken() {
        return messageToken;
    }

    public void setMessageToken(String messageToken) {
        this.messageToken = messageToken;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(UserID);
        parcel.writeString(UserNickName);
        parcel.writeStringList(Top3Choice);
        parcel.writeStringList(vote);
        parcel.writeString(avatarUrl);
    }
}
