package com.example.cumessengerapp.models;

import java.util.ArrayList;

public class UserStatus {
    private String name,profileImage,clgUid,uId;
    private long lastUpdated;
    private ArrayList<Status> statuses;

    public UserStatus(String name, String profileImage, String clgUid, String uId, long lastUpdated, ArrayList<Status> statuses) {
        this.name = name;
        this.profileImage = profileImage;
        this.clgUid = clgUid;
        this.uId = uId;
        this.lastUpdated = lastUpdated;
        this.statuses = statuses;
    }

    public UserStatus() {
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getClgUid() {
        return clgUid;
    }

    public void setClgUid(String clgUid) {
        this.clgUid = clgUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }
}
