package com.example.unlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText userName,password;

    Button registerbtn,loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName=findViewById(R.id.userName);
        password=findViewById(R.id.password);

        loginBtn=findViewById(R.id.loginBtn);
        registerbtn=findViewById(R.id.regiviewBtn);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivities(new Intent[]{intent});
                finish();

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=userName.getText().toString();
                String pw=password.getText().toString();

                SharedPreferences sharedPreferences=getSharedPreferences("userfile", Context.MODE_PRIVATE);
                String saveName=sharedPreferences.getString("UserName","");
                String savePasswd=sharedPreferences.getString("password","");

                if (name.equals(saveName) && pw.equals(savePasswd)){
                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(LoginActivity.this, HomeActivity.class);
                    startActivities(new Intent[]{intent});
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}