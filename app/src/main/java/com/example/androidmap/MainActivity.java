package com.example.androidmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.androidmap.InformationDB.OperateInDB;
import com.example.androidmap.InformationDB.record;
import com.example.androidmap.Login_and_Register.LoginActivity;
import com.example.androidmap.Login_and_Register.RegisterActivity;
import com.example.androidmap.UserDB.User;
import com.example.androidmap.UserDB.UserDB;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btnStart = null;
    private Button record = null;
    private TextView sum_len = null,xxxy = null;
    User u = LoginActivity.u;

    private ArrayAdapter adapter;
    //定义一个列表，储存信息。
    private ArrayList<record> Rec;

    private UserDB infoDB = new UserDB(this);
    private OperateInDB OpInfoDB = new OperateInDB(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btnStart);
        record = findViewById(R.id.Record);
        sum_len = findViewById(R.id.sumLen);
        xxxy = findViewById(R.id.xxxy_Text);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapActivity.class));
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecordActivity.class));
            }
        });
        cal_sum();

    }
    private void cal_sum(){
        int sum = 0;
        Rec = OpInfoDB.getInfo(LoginActivity.u.getUserName());
        if(Rec.size()==0) sum=0;
        else {
            for(record r:Rec){
                sum += Integer.valueOf(r.getLength());
            }}
        sum_len.setText(String.valueOf(sum));
        xxxy.setText("Equivalent to the School of\nEconomics and Management\n\t"+String.valueOf(sum/600)+"\tcircles");
    }
}
