package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mycard.R;

public class Request_Type extends AppCompatActivity {


    Button BtnNext , BtnBack;
    RadioGroup radioGroup;
    RadioButton rBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__type);

        BtnNext = findViewById(R.id.btnNext);
        BtnBack = findViewById(R.id.btnBack);
        radioGroup  = findViewById(R.id.radiog);

        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId ==-1)
                {
                    Toast.makeText(getApplicationContext(),"please select the request type", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    rBtn =  findViewById(selectedId);
                    Toast.makeText(getApplicationContext(),rBtn.getText(), Toast.LENGTH_SHORT).show();

                    // do something with your choice

                }

            }
        });



    }
}