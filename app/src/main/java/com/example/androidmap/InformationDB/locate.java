package com.example.androidmap.InformationDB;

public class locate {
    private double X,Y;
    locate(String X,String Y){
        this.X = Double.valueOf(X);
        this.Y = Double.valueOf(Y);
    }
    public double getX(){
        return X;
    }
    public  double getY(){
        return Y;
    }
}
