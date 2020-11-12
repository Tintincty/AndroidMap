package com.example.androidmap.Login_and_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.PeriodicSync;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmap.MainActivity;
import com.example.androidmap.MapActivity;
import com.example.androidmap.R;
import com.example.androidmap.UserDB.OperateDB;
import com.example.androidmap.UserDB.User;
import com.example.androidmap.UserDB.UserDB;

public class LoginActivity extends AppCompatActivity{
    private TextView UserName = null;
    private TextView Password = null;
    private Button btnLogin = null;
    private Button btnRegister = null;
    private CheckBox remenberBox = null;
    private SharedPreferences sharedPreferences;
    public static  User u;
    UserDB udb = null;
    OperateDB Odb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitLogin();

    }
    private void InitLogin()
    {
        UserName = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);
        remenberBox = findViewById(R.id.remenberCheckBox);
        sharedPreferences = getSharedPreferences("rememberpassword", Context.MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("rememberpassword", false);
        if (isRemember) {
            String name = sharedPreferences.getString("name", "");
            String password = sharedPreferences.getString("password", "");
            UserName.setText(name);
            Password.setText(password);
            remenberBox.setChecked(true);
        }

        udb = new UserDB(this);
        Odb = new OperateDB(this);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = new User(UserName.getText().toString(),Password.getText().toString());
                if(Odb.find(u))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (remenberBox.isChecked()) {
                        editor.putBoolean("rememberpassword", true);
                        editor.putString("name", UserName.getText().toString());
                        editor.putString("password", Password.getText().toString());
                    } else {
                        editor.clear();
                    }
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    Toast.makeText(getApplicationContext(),"login successful！", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Incorrect username or password！", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

