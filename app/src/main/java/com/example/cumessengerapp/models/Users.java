package com.example.cumessengerapp.models;

public class Users {
    String profilepic,userName,clgUid,email,password,lastMessage,userId;

    public Users(String profilepic, String userName, String clgUid, String email, String password, String lastMessage, String userId) {
        this.profilepic = profilepic;
        this.userName = userName;
        this.clgUid = clgUid;
        this.email = email;
        this.password = password;
        this.lastMessage = lastMessage;
        this.userId = userId;
    }
    public Users(){}
    //signup constructor
    public Users(String userName, String clgUid, String email, String password) {
        this.userName = userName;
        this.clgUid = clgUid;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClgUid() {
        return clgUid;
    }

    public void setClgUid(String clgUid) {
        this.clgUid = clgUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }


}
