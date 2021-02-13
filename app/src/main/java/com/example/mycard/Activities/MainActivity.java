package com.example.mycard.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mycard.R;
import com.example.mycard.helper.Session;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Session session;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(getApplicationContext());
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

                    if (session.getType().equals("DEN"))
                    {
                        Intent intent = new Intent(getApplicationContext(), Deanship.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        }, 2500);
    }




}