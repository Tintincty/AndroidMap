package com.example.androidmap.UserDB;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDB extends SQLiteOpenHelper {

    public static final String USER_TABLE_NAME = "User";
    public static final String Info_TABLE_NAME = "info";
    public static final String Number = "UserNumber";
    public static final String Password = "Password";
    public static final String DATABASE_NAME = "use";
    public static final String User = "user";
    public static final String Date = "date";
    public static final String Length = "length";
    public static final String Trace_Table_Name = "trace";
    public static final String X = "x";
    public static final String Y = "y";

    public static final int VERSION = 1;
    private static final String TAG = "SQLiteOpenHelper";

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);Log.d(TAG,"数据库创建");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +USER_TABLE_NAME + "(" + Number + " char(11) not null," + Password + " varchar(20) not null" + ")");
        Log.d(TAG,"用户表创建");
        db.execSQL("create table " +Info_TABLE_NAME + "(" + User + " char(11) not null," + Date + " varcahr(30) not null,"
                + Length + " varcahr(10) not null"+")");
        Log.d(TAG,"信息表创建");
        db.execSQL("create table " +Trace_Table_Name + "(" + User + " char(11) not null," + Date + " varcahr(30) not null,"
                + X + " varcahr(100) not null,"+Y+" varcahr(100) not null )");
        Log.d(TAG,"路径表创建");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
