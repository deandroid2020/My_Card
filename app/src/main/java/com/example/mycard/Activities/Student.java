package com.example.mycard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.mycard.R;
import com.google.android.material.navigation.NavigationView;

public class Student extends AppCompatActivity {

    private DrawerLayout mdrawerLayout ;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        mdrawerLayout= findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this , mdrawerLayout , R.string.open , R.string.close);
        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
        getSupportActionBar().setTitle("Here We Go ");



        NavigationView nav_view= findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();

                if (id == R.id.MemberLogOut) {

                    Toast.makeText(getApplicationContext() , "Log Out" , Toast.LENGTH_SHORT).show();

                }

                return true;
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void ToLost(View view) {
        startActivity(new Intent(getApplicationContext(), Lost_Card.class));
    }

    public void ToForget(View view) {
            startActivity(new Intent(getApplicationContext() , Forget_Card.class));
    }
}