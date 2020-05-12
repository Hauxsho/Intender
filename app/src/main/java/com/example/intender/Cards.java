package com.example.intender;

public class Cards
{
    private String userID,name;
    public Cards(String userID , String name) {
        this.userID = userID;
        this.name = name;
    }

    public Cards() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
