package com.example.starbidz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class No_Internet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button btn_retry = (Button)findViewById(R.id.btn_retry);
        btn_retry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i1 = new Intent(No_Internet.this,Check_Internet.class);
                startActivity(i1);

                Toast.makeText(No_Internet.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}