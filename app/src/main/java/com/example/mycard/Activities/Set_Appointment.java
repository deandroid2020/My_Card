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
    String ReqID;
    private Spinner spinner;
    ArrayAdapter sptype;

    Session session ;

    DatePickerDialog datePickerDialog;

    Button ChDate , SendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__appointment);
        initViews();

        Intent intent = getIntent();
        ReqID = intent.getStringExtra("ReqID");

        session = new Session(getApplicationContext());

        spinner=findViewById(R.id.spn);
        spinner.setAdapter(sptype);

        sptype  = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item);


        ChDate = findViewById(R.id.BtnChDate);
        SendBtn = findViewById(R.id.set_APT_Btn);

        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ChDate.getText().toString().equals("اختر التاريخ")){
                    Toast.makeText(getApplicationContext() , "الرجاء اختيار تاريخ الموعد " , Toast.LENGTH_LONG).show();
                }
                else if (spinner.getScrollBarSize() == 0){
                    Toast.makeText(getApplicationContext() , "الرجاء اختيار التاريخ و الوقت" , Toast.LENGTH_LONG).show();
                }
             else  if (spinner.getSelectedItemPosition() == 0){
                    Toast.makeText(getApplicationContext() , "الرجاء اختيار وقت الموعد " , Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext() , ChDate.getText().toString() +"-"+ spinner.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
                    SendApt (ReqID , ChDate.getText().toString() , spinner.getSelectedItem().toString());
                }

            }
        });


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

                        cmonth++;

                        ChDate.setText(cyear +"-"+cmonth+"-"+cdate);

                        getAvaTimes(cyear +"-"+cmonth+"-"+cdate);

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String ss = sptype.getItem(i).toString();
            //    Toast.makeText(getApplicationContext() , ss , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    } // end on create


    private void getAvaTimes(final String date) {


        sptype.clear();

        sptype.add("أختر الوقت ");
        sptype.add("08:00:00");
        sptype.add("09:00:00");
        sptype.add("10:00:00");
        sptype.add("11:00:00");
        sptype.add("12:00:00");
        sptype.add("13:00:00");
        sptype.add("14:00:00");
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, WebServices.URL_GetAVilableApt, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "getAvaTimes Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error)
                    {
                        JSONArray RequestArray = jObj.getJSONArray("Result");
                        {
                            for (int i=0 ;i<RequestArray.length() ; i++){
                                JSONObject ResultObject = RequestArray.getJSONObject(i);
                                if( !ResultObject.getString("Date").isEmpty()){
                                    sptype.remove(ResultObject.getString("time"));
                                }
                            }
                            sptype.notifyDataSetChanged();
                            spinner.setAdapter(sptype);
                        }

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Log.e(TAG, "getAvaTimes Error: " + errorMsg);
                        sptype.notifyDataSetChanged();
                        spinner.setAdapter(sptype);

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "getAvaTimes Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("date", date);
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


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



    private void SendApt (final  String ReqID , final String Date , final  String Time){
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, WebServices.URL_insertApt, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "SendApt Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error)
                    {

                        Toast.makeText(getApplicationContext() , "تم تسجيل الموعد " , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext() , Deanship.class));


                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Log.e(TAG, "SendApt Error: " + errorMsg);
                        Toast.makeText(getApplicationContext() , " لم يتم تسجيل الموعد الرجاء التاكد من البيانات او المحاولة لاحقا " , Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "SendApt Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("Request_ID", ReqID);
                params.put("Date", Date);
                params.put("time", Time);
                params.put("appt_Status", "booked");
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    }