package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import com.example.mycard.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivity(new Intent(getApplicationContext() , UserChoice.class));



        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toto);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
//        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("fsdf");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
     //   getSupportActionBar().setLogo(R.drawable.wo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        Button button = findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , Student.class));
            }
        });

        //  startActivity(new Intent(getApplicationContext() , Student.class));

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}