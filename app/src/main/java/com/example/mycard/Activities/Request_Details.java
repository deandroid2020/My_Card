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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycard.R;
import com.example.mycard.helper.AppController;
import com.example.mycard.helper.ArabicNumber;
import com.example.mycard.helper.Session;
import com.example.mycard.helper.WebServices;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Request_Details extends AppCompatActivity {


    private String TAG = Request_Details.class.getSimpleName();

    Button ViewCard , Set_APT , closeReq;
    String ReqID , STDID;
    TextView req_del_name , req_id_number , req_college , req_cam_id , request_id , request_type ;
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    Session session ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__details);

        initViews();
        session = new Session(getApplicationContext());

        Intent intent = getIntent();
        ReqID = intent.getStringExtra("ReqID");
        STDID = intent.getStringExtra("STDID");

        GetSTDInfo(ReqID);


        req_del_name = findViewById(R.id.req_del_name);
        req_id_number = findViewById(R.id.req_id_number);
        req_college = findViewById(R.id.req_college);
        req_cam_id = findViewById(R.id.req_cam_id);
        request_id = findViewById(R.id.request_id);
        request_type = findViewById(R.id.request_type);
        Set_APT = findViewById(R.id.Set_APT);
        ViewCard = findViewById(R.id.req_details_view_card);
        closeReq = findViewById(R.id.closeReq);

        Set_APT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Set_Appointment.class);
                intent.putExtra("ReqID" , ReqID);
                startActivity(intent);
            }
        });

        closeReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseReq(ReqID);
            }
        });



        ViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent = new Intent(getApplicationContext() , STD_ID.class);
             intent.putExtra("STDID" , STDID);
             intent.putExtra("openBy" , "Den");
             startActivity(intent);
            }
        });

        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.Request_Details_navigation_view);
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
    }  // end on create

    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.Request_Details_drawer_layout);
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


    private void GetSTDInfo(final String Request_ID) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_GetReqDetails, new Response.Listener<String>()
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
                        JSONObject user = jObj.getJSONObject("Result");
                        {
                            req_id_number.setText(ArabicNumber.GetArNumbers(user.getString("Student_ID")));
                            req_del_name.setText(" "+user.getString("First_Name")+" "+ user.getString("Last_Name")+"  ");
                            req_college.setText(user.getString("College_Name"));
                            req_cam_id.setText(user.getString("Campus_Name")+"  ");
                            request_id.setText(ArabicNumber.GetArNumbers(user.getString("Request_ID")+"  "));

                            if (user.getString("Type_ID").equals("1")){
                                request_type.setText("طلب نسيان");
                            }else
                            {
                                request_type.setText("طلب فقدان");
                            }
                        }

                    } else {
                        String errorMsg = jObj.getString("error_msg");
          //              Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
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
                params.put("Request_ID", Request_ID);
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void CloseReq(final String Request_ID) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_CloseRequest, new Response.Listener<String>()
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

                        if (request_type.getText().toString().equals("طلب نسيان")){
                            UpdateCounter( STDID , 0);
                        }

                        Toast.makeText(getApplicationContext(), "تم اغلاق الطلب", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext() , Deanship.class));
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), "يوجد خطاء حاولي لاحق", Toast.LENGTH_LONG).show();
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
                params.put("Request_ID", Request_ID);
                params.put("Req_Status", "2");
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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


                    } else {
                        String errorMsg = jObj.getString("error_msg");
                     //   Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
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