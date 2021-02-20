package com.example.mycard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mycard.R;
import com.example.mycard.helper.Session;

import java.util.Locale;

public class Forget_Card extends AppCompatActivity {

    Session session;
    Button button;
    LinearLayout linearLayout;
    TextView counterText , WarningName , WarningText;
    int count;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__card);

        session = new Session(getApplicationContext());

        Intent intent = getIntent();
        count = intent.getIntExtra("Counter" , 10);



        button = findViewById(R.id.ShowQR);
        counterText = findViewById(R.id.TextCounter);
        WarningName = findViewById(R.id.warningName);
        WarningText = findViewById(R.id.warningText);
        linearLayout = findViewById(R.id.laylay);


//        if (a == 0) {
//            counterText.setText("1");
//            WarningName.setText(getString(R.string.remember));
//            WarningName.setTextColor(getColor(R.color.green));
//            WarningText.setText(getString(R.string.firsttime));
//        } else if (a == 1) {
//            counterText.setText("2");
//            WarningName.setText(getString(R.string.warning));
//            WarningName.setTextColor(getColor(R.color.orange));
//            WarningText.setText(getString(R.string.secondtime));
//        } else if (a == 2) {
//            counterText.setText("3");
//            WarningName.setText(getString(R.string.caution));
//            WarningText.setTextColor(getColor(R.color.red));
//            WarningText.setText(getString(R.string.lasttime));
//        }

        switch (count){
            case 0:
                counterText.setText("1");
                WarningName.setText(getString(R.string.remember));
                WarningName.setTextColor(getColor(R.color.green));
                WarningText.setText(getString(R.string.firsttime));
                button.setEnabled(true);
                linearLayout.setVisibility(View.GONE);
                break;

            case 1:
                counterText.setText("2");
                WarningName.setText(getString(R.string.warning));
                WarningName.setTextColor(getColor(R.color.orange));
                WarningText.setText(getString(R.string.secondtime));
                button.setEnabled(true);
                linearLayout.setVisibility(View.GONE);
                break;

            case 2:
                counterText.setText("3");
                WarningName.setText(getString(R.string.caution));
                WarningText.setTextColor(getColor(R.color.red));
                WarningText.setText(getString(R.string.lasttime));
                button.setEnabled(true);
                linearLayout.setVisibility(View.GONE);
                break;
            case 3:
                linearLayout.setVisibility(View.VISIBLE);
                counterText.setText("4");
                WarningName.setEnabled(false);
                WarningText.setTextColor(getColor(R.color.red));
                WarningText.setText(getString(R.string.you_have_to_pay_a_fine));
                button.setEnabled(false);
                break;

        }







        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , ShowQR.class));
            }
        });


    }

    public void ToPaymentInfo(View view) {
        startActivity(new Intent(getApplicationContext() , Payment_info.class));
    }

}