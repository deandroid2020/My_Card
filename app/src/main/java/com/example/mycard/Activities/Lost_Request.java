package com.example.mycard.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycard.Adapter.RequestAdapter;
import com.example.mycard.Model.Request;
import com.example.mycard.R;
import com.example.mycard.helper.Session;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Lost_Request extends AppCompatActivity {

    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;

    private ListView listView;
    private List<Request> requestList = new ArrayList<>();
    private RequestAdapter requestAdapter;

    TextView textView ;
    Session session ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost__request);
        textView = findViewById(R.id.LostCount);

        initViews();
        session = new Session(getApplicationContext());

        listView = findViewById(R.id.LostList);
        requestAdapter = new RequestAdapter(this , requestList);
        listView.setAdapter(requestAdapter);

        GetReq();




        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.lost_request_navigation_view);
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

    private void GetReq() {
        Request r = new Request();
        r.setRequest_ID(102030);
        r.setType_ID(1);
        r.setStatus(1);
        requestList.add(r);

        Request a = new Request();
        a.setRequest_ID(203040);
        a.setType_ID(2);
        a.setStatus(2);
        requestList.add(a);

        Request b = new Request();
        b.setRequest_ID(304050);
        b.setType_ID(1);
        b.setStatus(3);
        requestList.add(b);

//        Request c = new Request();
//        c.setRequest_ID(405060);
//        c.setType_ID(2);
//        c.setStatus(1);
//        requestList.add(c);

        textView.setText(requestList.size()+"");

        requestAdapter.notifyDataSetChanged();

    }

    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.lost_request_drawer_layout);
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