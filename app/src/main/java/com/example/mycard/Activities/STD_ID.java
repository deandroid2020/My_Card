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

import java.util.HashMap;
import java.util.Map;

public class STD_ID extends AppCompatActivity {

    private String TAG = STD_ID.class.getSimpleName();

    String STDID;

    TextView ID , Fname , Lname , ColName , CamName;
    int Counter;
    Button BtnConfirm;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_t_d__i_d);

        Intent intent = getIntent();
        STDID = intent.getStringExtra("STDID");

        ID = findViewById(R.id.stdidid);
        Fname = findViewById(R.id.stdidfname);
        Lname = findViewById(R.id.stdidlname);
        ColName = findViewById(R.id.stdidcolname);
        CamName = findViewById(R.id.stdidcamname);
        BtnConfirm = findViewById(R.id.confirm_button);
        imageView = findViewById(R.id.CardView);

        GetSTDInfo(STDID);



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
                            Counter = user.getInt("Counter");

                            if (Counter > 3  ){
                                BtnConfirm.setEnabled(false);
                            }
                            else
                            {
                                imageView.setImageResource(R.drawable.green_bage_circle);
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