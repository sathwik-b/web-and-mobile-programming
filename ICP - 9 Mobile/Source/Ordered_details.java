package com.example.pizzaapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;



public class Ordered_details extends AppCompatActivity {

    TextView summaryText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_details);
        summaryText1 = findViewById(R.id.summaryText);
        summaryText1.setText(Html.fromHtml("<u>Your Order Summary</u><br/><br/>"));
        if(getIntent() != null){
            summaryText1.append(getIntent().getStringExtra("orderSummary"));
        }else{
            summaryText1.setText("You have no orders !!");
        }
        summaryText1.append(Html.fromHtml("<br/>"));
        summaryText1.setVisibility(View.VISIBLE);
    }
}