package com.danghai.dangdhps10201.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.danghai.dangdhps10201.R;

public class WelcomeActivity extends AppCompatActivity {

    final int TIME = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //DELAY TIME TO BUNDLE
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                //Intent
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },TIME);//4000 ms
    }
}
