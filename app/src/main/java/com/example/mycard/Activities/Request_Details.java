package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mycard.R;

public class Request_Details extends AppCompatActivity {

    Button ViewCard;
    String ReqID , STDID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__details);


        Intent intent = getIntent();
        ReqID = intent.getStringExtra("ReqID");
        STDID = intent.getStringExtra("STDID");

        ViewCard = findViewById(R.id.req_details_view_card);

        ViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent = new Intent(getApplicationContext() , STD_ID.class);
             intent.putExtra("STDID" , "qrCode");
             intent.putExtra("openBy" , "Den");
             startActivity(intent);
            }
        });

    }
}