package com.example.mycard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.mycard.R;
import com.example.mycard.helper.ArabicNumber;
import com.example.mycard.helper.Session;
import com.google.android.material.navigation.NavigationView;

public class Lost_Card extends AppCompatActivity {



    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    Session session ;
    TextView textView;
    Button FondBtn , RequestBtn , ReceiveBtn;
    int Counter;

    ImageView toolbarimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost__card);
        initViews();
        session = new Session(getApplicationContext());

        Intent intent = getIntent();
        Counter = intent.getIntExtra("dateDifference" , 0);


        toolbarimage = findViewById(R.id.toolbar_image);
        toolbarimage.setVisibility(View.VISIBLE);

        textView = findViewById(R.id.lost_card_date);
        FondBtn = findViewById(R.id.lost_card_found);
        RequestBtn = findViewById(R.id.lost_card_request);
        ReceiveBtn = findViewById(R.id.lost_card_receive);


        textView.setText(ArabicNumber.GetArNumbers(Counter+""));

        if (Counter >= 0){
            RequestBtn.setEnabled(false);
            ReceiveBtn.setEnabled(false);
            FondBtn.setEnabled(true);
        }
        else if (Counter > 30  ) {

        }



        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.Lost_card_navigation_view);
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


    } // end on create

    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.Lost_card_drawer_layout);
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
