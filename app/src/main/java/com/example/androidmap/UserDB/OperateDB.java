package com.example.androidmap.UserDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OperateDB {
    private final UserDB mDB;
    private final String TAG = "OperateDB";
    public OperateDB(Context context)
    {
        mDB = new UserDB(context);
    }
    public void save(User u)
    {
        SQLiteDatabase db = mDB.getWritableDatabase();
        db.execSQL("INSERT INTO "+UserDB.USER_TABLE_NAME+"("+UserDB.Number+","+UserDB.Password+") values(?,?)",
                new String[]{u.getUserName(),u.getPassword()});
    }
    public void delete(User u)
    {
        SQLiteDatabase db = mDB.getWritableDatabase();
        db.execSQL("DELETE FROM User WHERE Number = ?",
                new String[]{u.getUserName()});
    }

    public void update(User u)
    {
        SQLiteDatabase db = mDB.getWritableDatabase();
        db.execSQL("UPDATE User SET Number = ?,Password = ? WHERE Number = ?",
                new String[]{u.getUserName(),u.getPassword(),u.getUserName()});
    }

    public boolean find(User u)
    {
        SQLiteDatabase db = mDB.getWritableDatabase();
        Cursor cursor = null;
        //存在数据才返回true
        try{
            cursor =  db.rawQuery("SELECT * FROM User WHERE UserNumber = ?",
                    new String[]{u.getUserName()});
        }catch(Exception e){
            return false;
        }
        if(cursor.moveToFirst()&&u.getPassword().equals(cursor.getString(cursor.getColumnIndex("Password"))))
        {
            cursor.close();
            Log.d(TAG,"登陆成功");
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    public long getCount()
    {
        SQLiteDatabase db = mDB.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT COUNT (*) FROM User",null);
        cursor.moveToFirst();
        long result = cursor.getLong(0);
        cursor.close();
        return result;
    }
}
