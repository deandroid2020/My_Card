package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mycard.R;

public class UserChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choice);
    }

    public void ToStd(View view) {
        Intent intent = new Intent(getApplicationContext() , Login.class);
      //  intent.putExtra("UserType" , "STU");
        startActivity(intent);
    }

    public void ToSec(View view) {
        Intent intent = new Intent(getApplicationContext() , Login.class);
 //       intent.putExtra("UserType" , "SEC");
        startActivity(intent);
    }

    public void ToDen(View view) {
        Intent intent = new Intent(getApplicationContext() , Login.class);
   //     intent.putExtra("UserType" , "DEN");
        startActivity(intent);
    }
}