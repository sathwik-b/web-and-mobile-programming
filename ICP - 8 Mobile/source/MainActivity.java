package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button Loginbutton;
    EditText enterName;
    EditText enterPassword;
    TextView welcomeLine;
    TextView forgotLine;
    String usrname, pswd;
    boolean mark= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterName = findViewById(R.id.edName);
        enterPassword = findViewById(R.id.edPassword);
        forgotLine = findViewById(R.id.forgotView);
        welcomeLine = findViewById(R.id.entryLine);
        usrname = enterName.getText().toString();
        pswd = enterPassword.getText().toString();
        Loginbutton = findViewById(R.id.loginButton);
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enterName.getText().toString().isEmpty() && !enterPassword.getText().toString().isEmpty()) {
                    if (enterName.getText().toString().equals("sathwik") && enterPassword.getText().toString().equals("123456"))
                    { mark = true; }
                }
                if (!mark)
                { forgotLine.setText("Incorrect Username or Password"); }
                else
                { reDirectToWelcomePage(); }
            }
        });

    }
    public void reDirectToWelcomePage () {
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
    }
}









