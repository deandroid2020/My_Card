package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mycard.R;

public class Lost_Card extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost__card);
    }

    public void ToFound(View view) {
        startActivity(new Intent(getApplicationContext() , Found_Card.class));
    }

    public void ToReqNewCard(View view) {
        startActivity(new Intent(getApplicationContext() , ReqNewCard.class));
    }

    public void ToMaminSTD(View view) {
        startActivity(new Intent(getApplicationContext() , Student.class));
    }
}