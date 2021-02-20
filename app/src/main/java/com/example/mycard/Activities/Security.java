package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mycard.R;
import com.example.mycard.helper.Session;

public class Security extends AppCompatActivity {

    Session session ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);


        session = new Session(getApplicationContext());


    }


    public void ToReadQR(View view) {
        startActivity(new Intent(getApplicationContext() , Reader.class));
    }

    public void LogOut(View view) {
        session.LogOut();
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
    }
}