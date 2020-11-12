package com.example.androidmap.InformationDB;


public class Information {
    private String user;
    private String date;
    private String length;

    public Information(String user, String date, String length) {
        this.user = user;
        this.date = date;
        this.length = length;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

}
