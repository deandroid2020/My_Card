package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mycard.helper.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Request_Details extends AppCompatActivity {

    private String TAG = Request_Details.class.getSimpleName();

    Button ViewCard , Set_APT;
    String ReqID , STDID;
    TextView req_del_name , req_id_number , req_college , req_cam_id , request_id , request_type ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__details);

        Intent intent = getIntent();
        ReqID = intent.getStringExtra("ReqID");
        STDID = intent.getStringExtra("STDID");

        req_del_name = findViewById(R.id.req_del_name);
        req_id_number = findViewById(R.id.req_id_number);
        req_college = findViewById(R.id.req_college);
        req_cam_id = findViewById(R.id.req_cam_id);
        request_id = findViewById(R.id.request_id);
        request_type = findViewById(R.id.request_type);
        Set_APT = findViewById(R.id.Set_APT);


        Set_APT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Set_Appointment.class);
                intent.putExtra("ReqID" , ReqID);
                startActivity(intent);
            }
        });





        ViewCard = findViewById(R.id.req_details_view_card);

        ViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent = new Intent(getApplicationContext() , STD_ID.class);
             intent.putExtra("STDID" , "qrCode");
             intent.putExtra("openBy" , "Den");
             startActivity(intent);
            }
        });

    }  // end on create


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
                        JSONObject user = jObj.getJSONObject("Result");

                        {
                            String fulltext = user.getString("Student_ID");
                            fulltext = fulltext.replace("0" , "٠").replace("1","١").replace("2","٢")
                                    .replace("3","٣").replace("4" , "٤").replace("5" ,"٥")
                                    .replace("6" ,"٦").replace("7" ,"٧").replace("8" , "٨").replace("9" , "٩");
                            req_id_number.setText(fulltext);

                            req_del_name.setText(" "+user.getString("first_name")+" "+ user.getString("last_name")+"  ");
                 //           name = user.getString("first_name");
                //            textView.setText(name);
//                            ColName.setText(user.getString("College_Name")+"  ");
//                            CamName.setText(user.getString("Campus_name")+"  ");
//                            Counter = user.getInt("Counter");
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






}