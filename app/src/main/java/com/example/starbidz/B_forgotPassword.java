package com.example.starbidz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class B_forgotPassword extends AppCompatActivity
{
    EditText edt_contact;
    Button btn_submit;
    TextView txt_back;

    ProgressDialog loadingDialog;

    String s_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_forgot_password);

        edt_contact = (EditText)findViewById(R.id.edt_contact);

        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                s_contact = edt_contact.getText().toString();

                if (s_contact.length()==0)
                {
                    edt_contact.setError("Enter Contact Number");
                }
                else if (s_contact.length()!=10)
                {
                    edt_contact.setError("Invalid Contact");
                }
                else
                {
                    b_frgtpass(s_contact);
                }
            }
        });


        txt_back = (TextView)findViewById(R.id.txt_back);
        txt_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i1 = new Intent(B_forgotPassword.this,B_login.class);
                startActivity(i1);
            }
        });


    }

    private void b_frgtpass(final String s_contact)
    {
        loadingDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
        loadingDialog.setMessage("Please wait ...");
        loadingDialog.show();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(1000);
                    loadingDialog.dismiss();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }).start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.B_FORGOT_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("fgh",response.toString());

                try {
                    JSONObject parentobject = new JSONObject(response.toString());

                    String status = parentobject.getString("status");
                    if (status.equals("success"))
                    {

                        String message = parentobject.getString("message");
                        Toast.makeText(B_forgotPassword.this, message, Toast.LENGTH_SHORT).show();

                        Intent i1 = new Intent(B_forgotPassword.this,B_login.class);
                        startActivity(i1);
                        finish();

                    }else{
                        String message = parentobject.getString("message");
                        Toast.makeText(B_forgotPassword.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e)
                {
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


                parameters.put("b_contact",s_contact);

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