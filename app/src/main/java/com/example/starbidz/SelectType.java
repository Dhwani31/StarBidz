package com.example.starbidz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.starbidz.Connection.ServerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SelectType extends AppCompatActivity
{
    ProgressBar progressbar;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);


        progressbar =(ProgressBar)findViewById(R.id.progressbar);


        //BUYER
        Button btn_buyer = (Button)findViewById(R.id.btn_buyer);
        btn_buyer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
                String b_id = Data_shpref.data1.getString("b_id",null);

                if (b_id == null)
                {
//                    Intent i1 = new Intent(SelectType.this,B_login.class);
//                    startActivity(i1);
//                    finish();
                }
                else
                {
//                    Intent i1 = new Intent(SelectType.this,B_home.class);
//                    startActivity(i1);
//                    finish();
                }

            }
        });



        //SELLER
        Button btn_seller = (Button)findViewById(R.id.btn_seller);
        btn_seller.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
                String s_id = Data_shpref.data1.getString("s_id",null);

                if (s_id == null)
                {
//                    Intent i1 = new Intent(SelectType.this,Login.class);
//                    startActivity(i1);
//                    finish();
                }
                else
                {
                    Intent i1 = new Intent(SelectType.this,MainActivity.class);
                    startActivity(i1);
                    finish();
                }
            }

        });


        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //Toast.makeText(this,date, Toast.LENGTH_SHORT).show();

        check_date(date);



    }

    private void check_date(final String date)
    {
        progressbar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.CHECK_TODAY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("fgh",response.toString());


                progressbar.setVisibility(View.INVISIBLE);


                try
                {
                    JSONObject parentobject = new JSONObject(response.toString());

                    String status = parentobject.getString("status");

                    if (status.equals("success"))
                    {
//                        String message = parentobject.getString("message");
//                        Toast.makeText(SelectType.this, message, Toast.LENGTH_SHORT).show();


//                        Intent i1 = new Intent(SelectType.this,SelectType.class);
//                        startActivity(i1);
//                        finish();

                    }
                    else
                    {
//                        String message = parentobject.getString("message");
//                        Toast.makeText(SelectType.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fgh",error.toString());
                progressbar.setVisibility(View.INVISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("date_today",date);

                return parameters;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}