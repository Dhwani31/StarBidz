package com.example.starbidz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class B_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_home);



        ImageView b_profile = (ImageView)findViewById(R.id.b_profile);
        b_profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent i1 = new Intent(B_home.this,B_profile.class);
//                startActivity(i1);
            }
        });



        ImageView b_property = (ImageView)findViewById(R.id.b_property);
        b_property.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent i1 = new Intent(B_home.this,B_all_property.class);
//                startActivity(i1);
            }
        });



        ImageView b_auction = (ImageView)findViewById(R.id.b_auction);
        b_auction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent i1 = new Intent(B_home.this,B_myAuction.class);
//                startActivity(i1);
            }
        });



        ImageView b_result = (ImageView)findViewById(R.id.b_result);
        b_result.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent i1 = new Intent(B_home.this,B_result.class);
//                startActivity(i1);
            }
        });



        ImageView b_about = (ImageView)findViewById(R.id.b_about);
        b_about.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent i1 = new Intent(B_home.this,B_about.class);
//                startActivity(i1);
            }
        });



        ImageView b_logout = (ImageView)findViewById(R.id.b_logout);
        b_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder= new AlertDialog.Builder(B_home.this);
                builder.setCancelable(false)
                        .setMessage("Are you sure want to Logout?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            public SharedPreferences.Editor data1;

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
                                String data = Data_shpref.data1.getString("b_id",null);

                                this.data1 = Data_shpref.data1.edit();
                                this.data1.putString("b_id",null);
                                this.data1.commit();

                                Intent i1 = new Intent(B_home.this,SelectType.class);
                                startActivity(i1);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Logout");
                alertDialog.show();
            }
        });


    }
}