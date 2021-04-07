package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycard.R;
import com.example.mycard.helper.AppController;
import com.example.mycard.helper.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class STD_ID extends AppCompatActivity {

    private String TAG = STD_ID.class.getSimpleName();
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;

    Session session ;
    String STDID , openBy;
    TextView ID , Fname , Lname , ColName , CamName , GovID , ID_Exp , Program ;
    int Counter;
    Button BtnConfirm , BtnDeny;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_t_d__i_d);

        initViews();
        session = new Session(getApplicationContext());

        Intent intent = getIntent();
        STDID = intent.getStringExtra("STDID");
        openBy = intent.getStringExtra("openBy");

        ID = findViewById(R.id.stdidid);
        Fname = findViewById(R.id.stdidfname);
        Lname = findViewById(R.id.stdidlname);
        ColName = findViewById(R.id.stdidcolname);
        CamName = findViewById(R.id.stdidcamname);
        GovID = findViewById(R.id.stdGovID);
        ID_Exp = findViewById(R.id.stdIDExp);
        Program = findViewById(R.id.stdprogr);
        BtnConfirm = findViewById(R.id.confirm_button);
        BtnDeny = findViewById(R.id.deny_button);
        imageView = findViewById(R.id.CardView);

        if (openBy.equals("Den")){
            BtnConfirm.setVisibility(View.GONE);
            BtnDeny.setVisibility(View.GONE);
        }

        BtnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Counter >= 3) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    addForgetRequest(STDID, dateFormat.format(new Date()));
                }
            }
        });

        GetSTDInfo(STDID);

        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.STD_ID_navigation_view);
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

    }

    private void addForgetRequest(final String STDID ,final String date) {

        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_addRequest, new Response.Listener<String>()
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
                        if (jObj.getBoolean("Result")){
                            Toast.makeText(getApplicationContext() , "تم تسجيل طلب فقدان ويجب مراجعة عمادة القبول والتسجيل  " , Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext() , Security.class));
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext() , "حدثت مشكلة في النظام ولم يتم تسجيل طلب فقدان بطاقة " , Toast.LENGTH_LONG).show();
                        }

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Log.e(TAG, "Login Error: " + errorMsg);
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
                params.put("STDID", STDID);
                params.put("Type_ID", "1");
                params.put("Req_Status", "1");
                params.put("Req_Date", date);
                params.put("Apt_Status", "1");

                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }


    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.STD_ID_drawer_layout);
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


    private void GetSTDInfo(final  String User_ID) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_getSTDByUserId, new Response.Listener<String>()
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
                        JSONObject user = jObj.getJSONObject("result");

                        {

                            String fulltext = user.getString("student_id");
                            fulltext = fulltext.replace("0" , "٠").replace("1","١").replace("2","٢")
                                    .replace("3","٣").replace("4" , "٤").replace("5" ,"٥")
                                    .replace("6" ,"٦").replace("7" ,"٧").replace("8" , "٨").replace("9" , "٩");
                            ID.setText(fulltext);

                            Fname.setText(user.getString("first_name"));
                            Lname.setText(user.getString("last_name"));
                            ColName.setText(user.getString("College_Name"));
                            CamName.setText(user.getString("Campus_name"));
                            Program.setText(user.getString("Program_type"));

                             fulltext = user.getString("Identity_ID");
                            fulltext = fulltext.replace("0" , "٠").replace("1","١").replace("2","٢")
                                    .replace("3","٣").replace("4" , "٤").replace("5" ,"٥")
                                    .replace("6" ,"٦").replace("7" ,"٧").replace("8" , "٨").replace("9" , "٩");
                            GovID.setText(fulltext);

                             fulltext = user.getString("Expression_Date");
                            fulltext = fulltext.replace("0" , "٠").replace("1","١").replace("2","٢")
                                    .replace("3","٣").replace("4" , "٤").replace("5" ,"٥")
                                    .replace("6" ,"٦").replace("7" ,"٧").replace("8" , "٨").replace("9" , "٩");
                            ID_Exp.setText(fulltext);


                            Counter = user.getInt("Counter");

                            if (Counter >= 3  ){
                                BtnConfirm.setEnabled(false);
                                imageView.setImageResource(R.drawable.bage_circle);
                            }
                            else
                            {
                                imageView.setImageResource(R.drawable.green_bage_circle);
                            }
                        }

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Log.e(TAG, "Login Error: " + errorMsg);
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
                params.put("User_ID", User_ID);
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void Confirm(View view) {

        if (Counter < 3 ){
            UpdateCounter(STDID , Counter+1);
        }
        else
        {
            Toast.makeText(getApplicationContext() , "تم نسيان البطاقة اكثر من ثلاثة مرات" , Toast.LENGTH_LONG).show();
            BtnConfirm.setEnabled(false);
        }

    }

    private void UpdateCounter(final  String User_ID , final  int count) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_UpdateCounter, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update Counter Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error)
                    {
                        // user successfully logged in
                        boolean user = jObj.getBoolean("user");

                        if (user)
                        {
                            startActivity(new Intent(getApplicationContext() , Security.class));
                            finish();
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
                params.put("Student_ID", User_ID);
                params.put("Counter", String.valueOf(count));
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}