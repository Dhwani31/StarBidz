package com.example.starbidz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity
{
    EditText edt_feedback;
    Button btn_submit;

    Dialog loadingDialog;
    String s_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
        s_id = Data_shpref.data1.getString("s_id",null);



        edt_feedback = (EditText)findViewById(R.id.edt_feedback);


        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                try
                {
                    String s_message = edt_feedback.getText().toString();

                    if (s_message.length() == 0)
                    {
                        edt_feedback.setError("Enter Message");
                    }
                    else
                    {
                        feedback(s_id,s_message);
                        //Toast.makeText(Feedback.this, data+s_message, Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });


    }

    private void feedback(final String s_id, final String s_message)
    {
        loadingDialog = ProgressDialog.show(Feedback.this, "Please wait", "Loading...");
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(500);
                    loadingDialog.dismiss();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }).start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.S_FEEDBACK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("fgh",response.toString());




                try
                {
                    JSONObject parentobject = new JSONObject(response.toString());

                    String status = parentobject.getString("status");

                    if (status.equals("success"))
                    {
                        String message = parentobject.getString("message");
                        Toast.makeText(Feedback.this, message, Toast.LENGTH_SHORT).show();


                        Intent i1 = new Intent(Feedback.this,MainActivity.class);
                        startActivity(i1);
                        finish();

                    }
                    else
                    {
                        String message = parentobject.getString("message");
                        Toast.makeText(Feedback.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fgh",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("feed_s_id",s_id);
                parameters.put("feed_message",s_message);

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

    @Override
    public void onBackPressed()
    { }


}