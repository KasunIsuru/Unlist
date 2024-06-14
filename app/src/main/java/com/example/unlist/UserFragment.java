package com.example.unlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UserFragment extends Fragment {
    TextView name,password;

    ImageButton imageButton;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);


        name=view.findViewById(R.id.showname);
        password=view.findViewById(R.id.showpassword);

        imageButton=view.findViewById(R.id.logoutBtn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        SharedPreferences sharedPreferences=requireActivity().getSharedPreferences("userfile", Context.MODE_PRIVATE);
        String names=sharedPreferences.getString("UserName","Name");
        String pwd=sharedPreferences.getString("password","Password");

        name.setText(names);
        password.setText(pwd);


        return view;


    }

    private void logout() {
        Toast.makeText(getActivity(),"SuccessFul logout",Toast.LENGTH_SHORT).show();

        Intent intent =new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();


    }
}