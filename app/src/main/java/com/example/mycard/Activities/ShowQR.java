package com.example.mycard.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.mycard.R;
import com.example.mycard.helper.Session;
import com.google.android.material.navigation.NavigationView;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class ShowQR extends AppCompatActivity {

    Session session;
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;

    ImageView toolbarimage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_q_r);
        initViews();
        session = new Session(getApplicationContext());

        Bitmap bitmap;
        ImageView qrImage;
        qrImage = findViewById(R.id.GeneQR);

        toolbarimage = findViewById(R.id.toolbar_image);
        toolbarimage.setVisibility(View.VISIBLE);



        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(session.getId()+"", null, QRGContents.Type.TEXT, 650);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);
        // Getting QR-Code as Bitmap
        bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        qrImage.setImageBitmap(bitmap);


        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.ShowQR_navigation_view);
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

                if (id == R.id.Contact_us_menu){
                    startActivity(new Intent(getApplicationContext() , Contat_US.class));
                }

                return true;
            }
        });

    } // end on create

    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.ShowQR_drawer_layout);
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