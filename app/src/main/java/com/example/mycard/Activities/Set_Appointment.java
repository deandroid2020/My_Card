  package com.example.mycard.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycard.Model.Request;
import com.example.mycard.R;
import com.example.mycard.helper.AppController;
import com.example.mycard.helper.Session;
import com.example.mycard.helper.WebServices;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Set_Appointment extends AppCompatActivity {

    private String TAG = Set_Appointment.class.getSimpleName();

    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;

    private Spinner spinner;
    String [][] names = {{""},{""} };
    ArrayAdapter sptype;

    Session session ;

    DatePickerDialog datePickerDialog;

    Button ChDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__appointment);

        initViews();

        session = new Session(getApplicationContext());

        spinner=findViewById(R.id.spn);
        spinner.setAdapter(sptype);



        ChDate = findViewById(R.id.BtnChDate);


        ChDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // time picker dialog
                datePickerDialog = new DatePickerDialog(Set_Appointment.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int cyear, int cmonth, int cdate) {


                        ChDate.setText(cyear + "- Year -" + cmonth + "- Month -" + cdate );
                        Toast.makeText(getApplicationContext() , "Mon"+cdate , Toast.LENGTH_SHORT).show();




                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                


            }
        });



        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        NavigationView nav_view= findViewById(R.id.Set_Appointment_navigation_view);
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
        mDrawerLayout = findViewById(R.id.Set_Appointment_drawer_layout);
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


/*
    private void RefreshTimeList (){
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

                        {

                            for (int i=0 ;i<RequestArray.length() ; i++){

                                JSONObject RequestObject = RequestArray.getJSONObject(i);
                                Request r = new Request();
                                r.setRequest_ID(RequestObject.getInt("Request_ID"));
                                r.setStudent_ID(RequestObject.getInt("Student_ID"));
                                r.setAppointment(RequestObject.getInt("Apt_Status"));
                                requestList.add(r);
                            }
                            String fulltext = requestList.size()+"";
                            fulltext = fulltext.replace("0" , "٠").replace("1","١").replace("2","٢")
                                    .replace("3","٣").replace("4" , "٤").replace("5" ,"٥")
                                    .replace("6" ,"٦").replace("7" ,"٧").replace("8" , "٨").replace("9" , "٩");
                            textView.setText(fulltext);
                            requestAdapter.notifyDataSetChanged();
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
                params.put("Type_ID", "1");
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
*/

    public void Can(View view) {
        names = new String[][] {{""} , {""}};
        sptype  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,names);
        spinner.setAdapter(sptype);

        Toast.makeText(getApplicationContext() , names.length+"" , Toast.LENGTH_SHORT).show();
    }

    public void ss(View view) {

        Object a= "a";
        Object b= "b";
        sptype  = new ArrayAdapter(this,android.R.layout.simple_spinner_item);
        sptype.add(a);
        sptype.add(b);
        sptype.notifyDataSetChanged();
        spinner.setAdapter(sptype);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String ss = sptype.getItem(i).toString();
                Toast.makeText(getApplicationContext() , ss , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}