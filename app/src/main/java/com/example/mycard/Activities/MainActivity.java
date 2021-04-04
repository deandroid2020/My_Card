package com.example.mycard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mycard.R;
import com.example.mycard.helper.Session;

public class MainActivity extends AppCompatActivity {

    Session session;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(getApplicationContext());

     //   startActivity(new Intent(getApplicationContext() , Set_Appointment.class));

      GoToPage();
    }

    public void GoToPage()
    {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if ( session.getSaved() == false )
                {
                    session.LogOut();
                    Intent intent = new Intent(getApplicationContext(), UserChoice.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    if (session.getType().equals("STU"))
                    {
                        Intent intent = new Intent(getApplicationContext(), Student.class);
                        intent.putExtra("STUID" , session.getId());
                        startActivity(intent);
                        finish();
                    }

                    if (session.getType().equals("SEC"))
                    {
                        Intent intent = new Intent(getApplicationContext(), Security.class);
                        startActivity(intent);
                        finish();
                    }

                    if (session.getType().equals("D"))
                    {
                        Intent intent = new Intent(getApplicationContext(), Deanship.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        }, 1500);
    }



}