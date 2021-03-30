package com.example.mycard.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycard.R;
import com.example.mycard.helper.Session;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class Forget_Card extends AppCompatActivity {

    private String TAG = Forget_Card.class.getSimpleName();

    Session session;
    Button button;
    LinearLayout linearLayout , warLayout;
    TextView counterText , WarningName , WarningText;
    int count;

    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    ImageView toolbarimage;
    String name;
    TextView textView;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__card);
        initViews();

        toolbarimage = findViewById(R.id.toolbar_image);
        toolbarimage.setVisibility(View.VISIBLE);

        session = new Session(getApplicationContext());

        Intent intent = getIntent();
        count = intent.getIntExtra("Counter" , 10);



        button = findViewById(R.id.ShowQR);
        counterText = findViewById(R.id.TextCounter);
        WarningName = findViewById(R.id.warningName);
        WarningText = findViewById(R.id.warningText);
        linearLayout = findViewById(R.id.laylay);
        warLayout = findViewById(R.id.warning_layout);



        textView = findViewById(R.id.UserName);

        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.forget_navigation_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();

                if (id == R.id.Logout_menu) {
                    session.LogOut();
                    Toast.makeText(getApplicationContext() , "خروج" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext() , UserChoice.class));
                }
                if (id == R.id.main) {
                    startActivity(new Intent(getApplicationContext() , MainActivity.class));
                }

                // pop up for concat us

                // notification
                return true;
            }
        });




        switch (4){
//            case 0:
//                warLayout.setVisibility(View.GONE);
//                counterText.setText(" ");
//                WarningName.setText(getString(R.string.remember));
//                WarningName.setTextColor(getColor(R.color.green));
//                WarningText.setText(getString(R.string.firsttime));
//                button.setEnabled(true);
//                linearLayout.setVisibility(View.GONE);
//
//                break;
            case 1:
                counterText.setText("١");
                WarningName.setText(getString(R.string.remember));
                WarningName.setTextColor(getColor(R.color.green));
                WarningText.setText(getString(R.string.firsttime));
                button.setEnabled(true);
                linearLayout.setVisibility(View.GONE);
                break;

            case 2:
                counterText.setText("٢");
                WarningName.setText(getString(R.string.warning));
                WarningName.setTextColor(getColor(R.color.orange));
                WarningText.setText(getString(R.string.secondtime));
                button.setEnabled(true);
                linearLayout.setVisibility(View.GONE);
                break;

            case 3:
                counterText.setText("٣");
                WarningName.setText(getString(R.string.caution));
                WarningText.setTextColor(getColor(R.color.red));
                WarningText.setText(getString(R.string.lasttime));
                button.setEnabled(true);
                linearLayout.setVisibility(View.GONE);
                break;
            case 4:
                linearLayout.setVisibility(View.VISIBLE);
                counterText.setText("٤");
             //   WarningName.setEnabled(false);
                WarningName.setVisibility(View.GONE);
                WarningText.setTextColor(getColor(R.color.red));
                WarningText.setText(getString(R.string.you_have_to_pay_a_fine));
                WarningText.setGravity(4);
                WarningText.setForegroundGravity(4);
                button.setEnabled(false);
                break;

        }







        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , ShowQR.class));
            }
        });


    }

    public void ToPaymentInfo(View view) {
        startActivity(new Intent(getApplicationContext() , Payment_info.class));
    }



    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.forget_drawer_layout);
        setUpToolbar();

    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.navigation_drawericon);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //    getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }

    public void  open (){
        mDrawerLayout.openDrawer(GravityCompat.START);
    }




}