package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycard.R;
import com.example.mycard.helper.AppController;
import com.example.mycard.helper.Session;
import com.example.mycard.helper.WebServices;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private String TAG = Login.class.getSimpleName();

    Button button;
    EditText username , password;
    String UserType;
    private CheckBox savel;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        UserType = intent.getStringExtra("UserType");

        button = findViewById(R.id.logingbtn);
        username = findViewById(R.id.UserName);
        password = findViewById(R.id.password);
        savel=findViewById(R.id.chk);

        checkLogin(username.getText().toString().trim() , password.getText().toString().trim() , UserType);

    }

    private void checkLogin(final String UserName, final String password , final String UserType) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_LOGIN, new Response.Listener<String>()
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
                        JSONObject user = jObj.getJSONObject("user");

                        String active= user.getString("Status");

                        if (active.equals("Not"))
                        {
                            Toast.makeText(getApplicationContext() , "الحساب غير مفعل" , Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            if (savel.isChecked())
                            {
                                session.setSaved(true);
                            }
                            else
                            {
                                session.setSaved(false);
                            }

                            session.setFName(user.getString("FName"));
                            session.setLName(user.getString("LName"));
                            session.setType(user.getString("Type"));
                            session.setId(Integer.parseInt(user.getString("ID")));


                            if (session.getType().equals("Student"))
                            {

                                Intent intent = new Intent(Login.this, Student.class);
                                startActivity(intent);
                                finish();
                            }

                            if (session.getType().equals("Security"))
                            {
                                Intent intent = new Intent(Login.this, Security.class);
                                startActivity(intent);
                                finish();
                            }

                            if (session.getType().equals("Deanship"))
                            {
                                Intent intent = new Intent(Login.this, Deanship.class);
                                startActivity(intent);
                                finish();
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
                params.put("UserName", UserName);
                params.put("Password", password);
                params.put("UserType", UserType);

                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }




}