package com.example.intender.Cards;

public class Cards
{
    private String userID;
    private String name;

    private String imageprofileURL;

    public Cards(String userID , String name ,String imageprofileURL) {
        this.userID = userID;
        this.name = name;
        this.imageprofileURL=imageprofileURL;
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

    public String getImageprofileURL() {
        return imageprofileURL;
    }

    public void setImageprofileURL(String imageprofileURL) {
        this.imageprofileURL = imageprofileURL;
    }



}
