package com.example.mycard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class Student extends AppCompatActivity {

    private String TAG = Student.class.getSimpleName();

    private DrawerLayout mdrawerLayout ;
    private ActionBarDrawerToggle mToggle;

    Session session ;

    TextView ID , Fname  , ColName , CamName;
    String name;
    TextView textView;
    int Counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        session = new Session(getApplicationContext());

        ID = findViewById(R.id.stuid);
        Fname = findViewById(R.id.stuname);
        ColName = findViewById(R.id.stucol);
        CamName = findViewById(R.id.stucam);


        Toolbar toolbar = findViewById(R.id.toto);
        toolbar.setTitle("");
        textView = findViewById(R.id.ss);

        setSupportActionBar(toolbar);


        mdrawerLayout= findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this , mdrawerLayout , R.string.open , R.string.close);
        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        NavigationView nav_view= findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();

                if (id == R.id.MemberLogOut) {

                    Toast.makeText(getApplicationContext() , "Log Out" , Toast.LENGTH_SHORT).show();

                }

                return true;
            }
        });

        GetSTDInfo(session.getId());

    }

    private void GetSTDInfo(final  int User_ID) {
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

                            ID.setText(" "+user.getString("student_id"));
                            Fname.setText(" "+user.getString("last_name")+" "+ user.getString("first_name")+"  ");
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void ToLost(View view) {
        startActivity(new Intent(getApplicationContext(), Lost_Card.class));
    }

    public void ToForget(View view) {
      //      startActivity(new Intent(getApplicationContext() , Forget_Card.class));
            Intent intent = new Intent(getApplicationContext() , Forget_Card.class);
            intent.putExtra("Counter" , Counter);
            startActivity(intent);
    }

    public void LogOut(View view) {
        session.LogOut();
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
    }
}