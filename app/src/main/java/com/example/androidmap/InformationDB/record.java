package com.example.androidmap.InformationDB;

public class record {
    private String user,date,length;
    public  record(String user,String date,String length){
        this.user = user;
        this.date = date;
        this.length = length;
    }
    public String getDate(){
        return date;
    }

    public String getInfo(){
        return "用户："+user +" 时间："+date+" 距离"+length+"米";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
