package com.example.starbidz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class Check_Internet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_internet);

        if (isConnectedNetwork())
        {

            Intent i1 = new Intent(Check_Internet.this,SelectType.class);
            startActivity(i1);
            finish();

        }
        else
        {
            Intent i1 = new Intent(Check_Internet.this,No_Internet.class);
            startActivity(i1);
            finish();
            //Toast.makeText(this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isConnectedNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo()!= null;
    }
}