package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Intent redirect = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getLocationDetails(View view) {
        redirect = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(redirect);
    }

    public void takePhoto(View view) {
        redirect = new Intent(MainActivity.this,CameraActivity.class);
        startActivity(redirect);
    }

    public void recordVoice(View view) {
        redirect = new Intent(MainActivity.this,RecordActivity.class);
        startActivity(redirect);
    }

    public void saveData(View view) {
        redirect = new Intent(MainActivity.this,StoreActivity.class);
        startActivity(redirect);
    }
}
