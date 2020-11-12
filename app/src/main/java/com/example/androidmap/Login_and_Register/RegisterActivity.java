package com.example.androidmap.Login_and_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmap.R;
import com.example.androidmap.UserDB.OperateDB;
import com.example.androidmap.UserDB.User;
import com.example.androidmap.UserDB.UserDB;


public class RegisterActivity extends AppCompatActivity {
    private TextView Phone = null;
    private TextView Password = null;
    private Button Register = null;

    UserDB udb = null;
    OperateDB Odb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Init();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = new User(Phone.getText().toString(),Password.getText().toString());
                Odb.save(u);
                Toast.makeText(getApplicationContext(),"registration success！", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void Init()
    {
        Phone = findViewById(R.id.Phone);
        Password = findViewById(R.id.Password);
        Register = findViewById(R.id.Register);
        udb = new UserDB(this);
        Odb = new OperateDB(this);
    }
}
