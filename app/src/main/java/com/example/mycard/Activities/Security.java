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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycard.R;
import com.example.mycard.helper.AppController;
import com.example.mycard.helper.Session;
import com.example.mycard.helper.WebServices;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Security extends AppCompatActivity {
    private String TAG = Student.class.getSimpleName();
    Session session ;
    TextView Name , ID , Cam , Gate;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    TextView textView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        initViews();
        textView = findViewById(R.id.UserName);

        session = new Session(getApplicationContext());

        Name = findViewById(R.id.EmpName);
        ID = findViewById(R.id.EmpID);
        Cam = findViewById(R.id.EmpCampNumber);
        Gate = findViewById(R.id.EmpGateNumber);

        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.Sec_navigation_view);
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

                return true;
            }
        });


        ID.setText(session.getId()+"");


        GetSecInfo(session.getId());
    }

    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.sec_drawer_layout);
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



    private void GetSecInfo(final  int User_ID) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_getSec_Info, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error)
                    {
                        // user successfully logged in
                        JSONObject user = jObj.getJSONObject("user");

                        Log.d("123" , user.toString());

                        {

                            String fulltext = user.getString("Employee_ID");
                            fulltext = fulltext.replace("0" , "٠").replace("1","١").replace("2","٢")
                                    .replace("3","٣").replace("4" , "٤").replace("5" ,"٥")
                                    .replace("6" ,"٦").replace("7" ,"٧").replace("8" , "٨").replace("9" , "٩");
                            ID.setText(fulltext);

                            Name.setText(" "+user.getString("First_Name")+" "+ user.getString("Last_Name")+"  ");
                            name = user.getString("First_Name");
                            textView.setText(name);
                            Cam.setText(" الفيصلية ");


                            String Gatetext = user.getString("Gate_ID");
                            Gatetext = Gatetext.replace("0" , "٠").replace("1","١").replace("2","٢")
                                    .replace("3","٣").replace("4" , "٤").replace("5" ,"٥")
                                    .replace("6" ,"٦").replace("7" ,"٧").replace("8" , "٨").replace("9" , "٩");
                            Gate.setText(Gatetext);

                        }

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("User_ID", String.valueOf(User_ID));
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void ToReadQR(View view) {
        startActivity(new Intent(getApplicationContext() , Reader.class));
    }

}