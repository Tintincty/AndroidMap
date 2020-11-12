package com.example.androidmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidmap.InformationDB.OperateInDB;
import com.example.androidmap.InformationDB.record;
import com.example.androidmap.Login_and_Register.LoginActivity;
import com.example.androidmap.UserDB.UserDB;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    ListView record;
    public ArrayAdapter adapter;
    //定义一个列表，储存信息。
    public ArrayList<String> infoList;
    public ArrayList<record> Rec;

    UserDB infoDB;
    OperateInDB OpInfoDB;
    public static String selectTime = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Init();
    }
    void Init(){
        infoDB = new UserDB(this);
        OpInfoDB = new OperateInDB(this);
        record = findViewById(R.id.record);
        Rec = OpInfoDB.getInfo(LoginActivity.u.getUserName());
        infoList = new ArrayList<String>();
        for(record r:Rec){
            infoList.add(r.getInfo());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, infoList);
        record.setAdapter(adapter);
        //定义列表Item的点击事件
        record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectTime = Rec.get(i).getDate();
                startActivity(new Intent(RecordActivity.this, TraceActivity.class));
            }


        });
    }

}
