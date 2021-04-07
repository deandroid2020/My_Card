package com.example.mycard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Lost_Card extends AppCompatActivity {

    private String TAG = Lost_Card.class.getSimpleName();

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

        if (Counter >= 0 && Counter <29){
            RequestBtn.setEnabled(false);
            ReceiveBtn.setEnabled(false);
            FondBtn.setEnabled(true);
        }
        else if (Counter >29  ) {
            RequestBtn.setEnabled(true);
            ReceiveBtn.setEnabled(false);
            FondBtn.setEnabled(false);
        }

        RequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                InsertLost(dateFormat.format(new Date()));
            }
        });


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

    private void InsertLost(final  String date) {
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

                        Toast.makeText(getApplicationContext() , "تم انشاء طلب بنجاح" , Toast.LENGTH_LONG).show();

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
                params.put("STDID", String.valueOf(session.getId()));
                params.put("Type_ID", "2");
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
}
