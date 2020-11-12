package com.example.androidmap.UserDB;

public class User//用户数据类
{
    private String UserName;
    private String Password;
    public User(String UserName,String Password)
    {
        this.UserName = UserName;
        this.Password = Password;
    }
    public String getUserName()
    {
        return UserName;
    }
    public String getPassword()
    {
        return Password;
    }
    public void setUserName(String UserName)
    {
        this.UserName = UserName;
    }
    public void setPassWord(String PassWord)
    {
        this.Password = PassWord;
    }
}
