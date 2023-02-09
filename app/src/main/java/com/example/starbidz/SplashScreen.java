package com.example.starbidz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();


        Thread splash = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(1500);
                    Intent i1 = new Intent(SplashScreen.this,Check_Internet.class);
                    startActivity(i1);
                    finish();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }
}