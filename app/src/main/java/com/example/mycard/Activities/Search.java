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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mycard.Adapter.RequestAdapter;
import com.example.mycard.R;
import com.example.mycard.helper.AppController;
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

public class Search extends AppCompatActivity {

    private String TAG = Search.class.getSimpleName();


    private Spinner TypeSpn , critSpn;
    Button button ;
    EditText editText;
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    String []   reqtype={"اختار نوع الطلب","طلب نسيان بطاقة" , "طلب فقدان بطاقة" };
    String []   critype={"اختار معيار البحث","برقم الطلب " , "برقم الطالبة" };
    Session session ;
    ListView listView;
    private List<com.example.mycard.Model.Request> requestList = new ArrayList<>();
    private RequestAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        session = new Session(getApplicationContext());
        TypeSpn = findViewById(R.id.reqTypeSpn);
        critSpn = findViewById(R.id.reqcrt);
        button = findViewById(R.id.SearchBtn);
        editText = findViewById(R.id.SearchEditText);
        listView = findViewById(R.id.SearchList);


        ArrayAdapter sptype  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,reqtype);
        TypeSpn.setAdapter(sptype);

        ArrayAdapter spCri = new ArrayAdapter(this , android.R.layout.simple_spinner_item , critype);
        critSpn.setAdapter(spCri);

        requestAdapter = new RequestAdapter(this , requestList);
        listView.setAdapter(requestAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext() , Request_Details.class);
                intent.putExtra("ReqID" , requestList.get(i).getRequest_ID()+"");
                intent.putExtra("STDID" , requestList.get(i).getStudent_ID()+"");
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestList.clear();
                requestAdapter.notifyDataSetChanged();

                if (TypeSpn.getSelectedItemId() == 0){
                    Toast.makeText(getApplicationContext() , "يجب اختيار نوع الطلب" , Toast.LENGTH_LONG).show();
                }
                else if (critSpn.getSelectedItemId() == 0){
                    Toast.makeText(getApplicationContext() , "يجب اختيار معيار البحث" ,Toast.LENGTH_LONG).show();
                }
                else if (editText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext() , "يجب عدم ترك الحقل فارغ " , Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (critSpn.getSelectedItemId() == 1 ){
                        SearchForReqInfo(editText.getText().toString());
                    }else {
                        SearchForReqInfoBySTUID(editText.getText().toString());
                    }

                }

            }
        });

        mToggle = new ActionBarDrawerToggle(this , mDrawerLayout , R.string.open , R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nav_view= findViewById(R.id.Search_navigation_view);
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


    } // in create

    // Tool Bar
    private void initViews() {
        mDrawerLayout = findViewById(R.id.Search_drawer_layout);
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

    private void SearchForReqInfo(final  String ID){
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_FindReqByID, new Response.Listener<String>()
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
                            for (int i=0 ;i<RequestArray.length() ; i++){

                                JSONObject RequestObject = RequestArray.getJSONObject(i);
                                com.example.mycard.Model.Request r = new com.example.mycard.Model.Request();
                                r.setRequest_ID(RequestObject.getInt("Request_ID"));
                                r.setStudent_ID(RequestObject.getInt("Student_ID"));
                                r.setAppointment(RequestObject.getInt("Apt_Status"));
                                requestList.add(r);
                            }
                            requestAdapter.notifyDataSetChanged();

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), "لايوجد طلب برقم الطلب المدخل ", Toast.LENGTH_LONG).show();
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
                params.put("Request_ID", ID);

                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void SearchForReqInfoBySTUID(final  String ID){
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST, WebServices.URL_FindReqBySTD, new Response.Listener<String>()
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
                        for (int i=0 ;i<RequestArray.length() ; i++){

                            JSONObject RequestObject = RequestArray.getJSONObject(i);
                            com.example.mycard.Model.Request r = new com.example.mycard.Model.Request();
                            r.setRequest_ID(RequestObject.getInt("Request_ID"));
                            r.setStudent_ID(RequestObject.getInt("Student_ID"));
                            r.setAppointment(RequestObject.getInt("Apt_Status"));
                            requestList.add(r);
                        }
                        requestAdapter.notifyDataSetChanged();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), "لايوجد طلب برقم الطالبة ", Toast.LENGTH_LONG).show();
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
                params.put("Student_ID", ID);

                return params;
            }
        };

        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}