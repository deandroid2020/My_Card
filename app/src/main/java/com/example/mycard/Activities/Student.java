package com.example.mycard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycard.R;
import com.example.mycard.helper.AppController;
import com.example.mycard.helper.Session;
import com.example.mycard.helper.WebServices;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Student extends AppCompatActivity {

    private String TAG = Student.class.getSimpleName();

    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    Session session ;

    TextView ID , Fname  , ColName , CamName;
    String name;
    TextView textView;
    int Counter;
    ImageView toolbarimage;

    String LostStatus = "New";
    String DateOfLost = "0000";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        initViews();
        session = new Session(getApplicationContext());

        ID = findViewById(R.id.stuid);
        Fname = findViewById(R.id.stuname);
        ColName = findViewById(R.id.stucol);
        CamName = findViewById(R.id.stucam);

        textView = findViewById(R.id.UserName);
        toolbarimage = findViewById(R.id.toolbar_image);
        toolbarimage.setVisibility(View.VISIBLE);


        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.stu_navigation_view);
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

        GetSTDInfo(session.getId());
        GetLostInfo(session.getId());

    } // end on create


    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.stu_drawer_layout);

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




    private void GetSTDInfo(final int User_ID) {
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

                            Fname.setText(" "+user.getString("first_name")+" "+ user.getString("last_name")+"  ");
                            name = user.getString("first_name");
                            textView.setText(name);
                            ColName.setText(user.getString("College_Name")+"  ");
                            CamName.setText(user.getString("Campus_name")+"  ");
                            Counter = user.getInt("Counter");
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


    private void GetLostInfo(final int Student_ID) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_GetReqLost, new Response.Listener<String>()
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

                        JSONArray RequestArray = jObj.getJSONArray("Result");
                        {

                            for (int i=0 ;i<RequestArray.length() ; i++){
                                JSONObject RequestObject = RequestArray.getJSONObject(i);

                                LostStatus = RequestObject.getString("Status");
                                DateOfLost = RequestObject.getString("Date_Of_Lost");
                            }
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
                params.put("Student_ID", String.valueOf(Student_ID));
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void InsertLost(final int Student_ID , final String Date_Of_Lost  ) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_InsertLost, new Response.Listener<String>()
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

                        Toast.makeText(getApplicationContext() , "تم انشاء طلب فقدان بطاقة" , Toast.LENGTH_LONG).show();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        int dateDifference = (int) getDateDiff(new SimpleDateFormat("yyyy-MM-dd"), dateFormat.format(new Date()) , dateFormat.format(new Date()));
                        System.out.println("dateDifference: " + dateDifference);

                        Intent intent = new Intent(getApplicationContext() , Lost_Card.class);
                        intent.putExtra("dateDifference" , dateDifference);
                        startActivity(intent);


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
                params.put("Student_ID", String.valueOf(Student_ID));
                params.put("Date_Of_Lost", Date_Of_Lost);
                params.put("Status", "Lost");
                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void ToLost(View view) {


                Log.d("123" , LostStatus + "-" + DateOfLost);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                if (LostStatus.equals("Lost")){
                    int dateDifference = (int) getDateDiff(new SimpleDateFormat("yyyy-MM-dd"), DateOfLost, dateFormat.format(new Date()));
                    Intent intent = new Intent(getApplicationContext() , Lost_Card.class);
                    intent.putExtra("dateDifference" , dateDifference);
                    startActivity(intent);
                }
                else {
                    InsertLost(session.getId() , dateFormat.format(new Date()));
                }
    }

    public void ToForget(View view) {
            if (Counter == 0 ){
                startActivity(new Intent(getApplicationContext() , ShowQR.class));
            }
            else {
                Intent intent = new Intent(getApplicationContext() , Forget_Card.class);
                intent.putExtra("Counter" , Counter);
                startActivity(intent);
            }
    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



}