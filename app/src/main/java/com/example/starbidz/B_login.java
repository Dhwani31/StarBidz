package com.example.starbidz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class B_login extends AppCompatActivity
{
    EditText edt_contact,edt_password;
    CheckBox check_password;
    TextView txt_forgot,txt_registration;
    Button btn_login;

    String s_contact,s_password;
    String b_id;

    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edt_contact = (EditText)findViewById(R.id.edt_contact);
        edt_password = (EditText)findViewById(R.id.edt_password);

        check_password =(CheckBox)findViewById(R.id.check_password);
        check_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if (b)
                {
                    edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        txt_forgot = (TextView)findViewById(R.id.txt_forgot);
        txt_forgot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i1 = new Intent(B_login.this,B_forgotPassword.class);
                startActivity(i1);
            }
        });



        txt_registration = (TextView)findViewById(R.id.txt_registration);
        txt_registration.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i1 = new Intent(B_login.this,B_generateOTP.class);
                startActivity(i1);
            }
        });


        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                s_contact = edt_contact.getText().toString();
                s_password = edt_password.getText().toString();

                if (s_contact.length()==0)
                {
                    edt_contact.setError("Enter Contact Number");
                }
                else if (s_contact.length()!=10)
                {
                    edt_contact.setError("Invalid Contact");
                }
                else if (s_password.length()==0)
                {
                    edt_password.setError("Enter Password");
                }
                else
                {
                    buyer_login(s_contact,s_password);
                }
            }
        });


    }

    private void buyer_login(final String s_contact, final String s_password)
    {
        loadingDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
        loadingDialog.setMessage("Please wait ...");
        //loadingDialog.show();

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.B_LOGIN, new Response.Listener<String>()
        {
            public SharedPreferences.Editor data1;

            @Override
            public void onResponse(String response) {
                Log.d("fgh", response.toString());
                //progressbar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject parentobject = new JSONObject(response.toString());

                    String status = parentobject.getString("status");
                    if (status.equals("success"))
                    {

                        JSONArray parentarray = parentobject.getJSONArray("data");

                        for (int i = 0; i<parentarray.length();i++)
                        {

                            JSONObject childdata = parentarray.getJSONObject(i);

                            b_id = childdata.getString("b_id");

                        }

                        Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
                        String data = Data_shpref.data1.getString("b_id",null);

                        this.data1 = Data_shpref.data1.edit();
                        this.data1.putString("b_id",b_id);
                        this.data1.commit();



                        Intent i1 = new Intent(B_login.this, com.example.starbidz.B_home.class);
                        String message = parentobject.getString("message");
                        Toast.makeText(B_login.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(i1);
                        finish();

                    }
                    else{
                        String message = parentobject.getString("message");
                        Toast.makeText(B_login.this, message, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fgh", error.toString());

                //progressbar.setVisibility(View.INVISIBLE);
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("b_contact",s_contact);
                parameters.put("b_password",s_password);
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