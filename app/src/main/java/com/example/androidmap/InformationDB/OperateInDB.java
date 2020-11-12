package com.example.androidmap.InformationDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.androidmap.UserDB.User;
import com.example.androidmap.UserDB.UserDB;

import java.util.ArrayList;

public class OperateInDB {
    private final UserDB mDB;
    private final String TAG = "OperateDB";
    public OperateInDB(Context context)
    {
        mDB = new UserDB(context);
    }
    public void save(Information info)
    {
        SQLiteDatabase db = mDB.getWritableDatabase();
        db.execSQL("INSERT INTO "+UserDB.Info_TABLE_NAME+"("+UserDB.User+","+UserDB.Date+","+UserDB.Length+") values(?,?,?)",
                new String[]{info.getUser(),info.getDate(),info.getLength()});
    }

    public void saveLocate(String u,String date,String X,String Y){
        SQLiteDatabase db = mDB.getWritableDatabase();
        db.execSQL("INSERT INTO "+UserDB.Trace_Table_Name+"("+UserDB.User+","+UserDB.Date+","+UserDB.X+", "+UserDB.Y+") values(?,?,?,?)",
                new String[]{u,date,X,Y});
    }

    public ArrayList<record> getInfo(String User){
        ArrayList<record> info =new ArrayList<>();
        SQLiteDatabase db = mDB.getWritableDatabase();
        Cursor cursor;
        //存在数据才返回true
        try{
            cursor =  db.rawQuery("SELECT * FROM "+UserDB.Info_TABLE_NAME+" WHERE "+UserDB.User+" = ?",
                    new String[]{User});
        }catch(Exception e){
            return null;
        }
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while(cursor.isAfterLast() == false)
            {
                String u = cursor.getString(cursor.getColumnIndex(UserDB.User));
                String t = cursor.getString(cursor.getColumnIndex(UserDB.Date));
                String l = cursor.getString(cursor.getColumnIndex(UserDB.Length));
                info.add(new record(u,t,l));
                cursor.moveToNext();
            }

        }
        return info;
    }

    public ArrayList<locate> getTrace(String time){
        ArrayList<locate> trace =new ArrayList<>();
        SQLiteDatabase db = mDB.getWritableDatabase();
        Cursor cursor;
        //存在数据才返回true
            try{
                cursor =  db.rawQuery("SELECT * FROM "+UserDB.Trace_Table_Name+" WHERE "+UserDB.Date+" = ?",
                        new String[]{time});
        }catch(Exception e){
            return null;
        }
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while(cursor.isAfterLast() == false)
            {
                trace.add(new locate(cursor.getString(cursor.getColumnIndex(UserDB.X)),cursor.getString(cursor.getColumnIndex(UserDB.Y))));
                cursor.moveToNext();
            }
        }
        return trace;
    }
}
