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

public class RegisterActivity extends AppCompatActivity {
    EditText userName, password, conPassword;

    Button loginBtn, registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName=findViewById(R.id.userName);
        password=findViewById(R.id.password);
        conPassword=findViewById(R.id.confirmPassword);

        registerBtn=findViewById(R.id.regiBtn);
        loginBtn =findViewById(R.id.loginViewBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivities(new Intent[]{intent});
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user =userName.getText().toString();
                String passwd=password.getText().toString();
                String cpasswd=conPassword.getText().toString();

                if(!user.isEmpty() && !passwd.isEmpty() && !cpasswd.isEmpty() ){
                    if (passwd.equals(cpasswd)){
                        saveData(user,passwd);
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivities(new Intent[]{intent});
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,"Password Do not Match",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"Please FIll all Feild",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void saveData(String name,String pw){
        SharedPreferences sharedPreferences= getSharedPreferences("userfile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("UserName",name);
        editor.putString("password",pw);
        editor.apply();
        Toast.makeText(RegisterActivity.this,"Register Successful",Toast.LENGTH_SHORT).show();
    }


}