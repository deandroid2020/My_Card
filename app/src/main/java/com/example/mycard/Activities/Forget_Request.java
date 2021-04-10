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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycard.Adapter.RequestAdapter;
import com.example.mycard.Model.Request;
import com.example.mycard.R;
import com.example.mycard.helper.AppController;
import com.example.mycard.helper.ArabicNumber;
import com.example.mycard.helper.Session;
import com.example.mycard.helper.WebServices;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Forget_Request extends AppCompatActivity {

    private String TAG = Forget_Request.class.getSimpleName();
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
        setContentView(R.layout.activity_forget__request);

        textView = findViewById(R.id.ForgetCount);

        initViews();
        session = new Session(getApplicationContext());

        listView = findViewById(R.id.ForgetList);
        requestAdapter = new RequestAdapter(this , requestList);
        listView.setAdapter(requestAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext() , requestList.get(i).getRequest_ID()+"" , Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext() , requestList.get(i).getStudent_ID()+"" , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext() , Request_Details.class);
                intent.putExtra("ReqID" , requestList.get(i).getRequest_ID()+"");
                intent.putExtra("STDID" , requestList.get(i).getStudent_ID()+"");
                startActivity(intent);
            }
        });

        GetReq();

        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.requests_navigation_view);
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


    private void GetReq() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, WebServices.URL_getRequestByType, new Response.Listener<String>()
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
                        JSONArray RequestArray = jObj.getJSONArray("Request");


                            for (int i=0 ;i<RequestArray.length() ; i++){
                                JSONObject RequestObject = RequestArray.getJSONObject(i);
                                Request r = new Request();
                                r.setRequest_ID(RequestObject.getInt("Request_ID"));
                                r.setStudent_ID(RequestObject.getInt("Student_ID"));
                                r.setAppointment(RequestObject.getInt("Apt_Status"));
                                requestList.add(r);
                            }
                            textView.setText(ArabicNumber.GetArNumbers(requestList.size()+""));
                            requestAdapter.notifyDataSetChanged();


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
                params.put("Type_ID", "1");
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.requests_drawer_layout);
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