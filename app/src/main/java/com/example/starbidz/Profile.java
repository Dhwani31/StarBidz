package com.iss.starbidz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iss.starbidz.Connection.ServerConfig;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity
{
    ImageView img_profile;
    TextView txt_name,txt_contact,txt_area,txt_address;

    String s_name,s_contact,s_area,s_address;
    String s_image,s_image_path,s_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        img_profile = (ImageView)findViewById(R.id.img_profile);

        txt_name = (TextView)findViewById(R.id.txt_name);
        txt_contact = (TextView)findViewById(R.id.txt_contact);
        txt_area = (TextView)findViewById(R.id.txt_area);
        txt_address = (TextView)findViewById(R.id.txt_address);

        Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
        s_id = Data_shpref.data1.getString("s_id",null);


        fetch_profile(s_id);
    }

    private void fetch_profile(final String s_id)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.S_PROFILE, new Response.Listener<String>() {

            public SharedPreferences.Editor data1;

            @Override
            public void onResponse(String response) {
                Log.d("fgh", response.toString());
                //progressbar.setVisibility(View.INVISIBLE);

                try
                {
                    JSONObject parentobject = new JSONObject(response.toString());

                    String status = parentobject.getString("status");

                    if (status.equals("success"))
                    {

                        JSONArray parentarray = parentobject.getJSONArray("data");

                        for (int i = 0; i<parentarray.length();i++)
                        {

                            JSONObject childdata = parentarray.getJSONObject(i);


                            s_name = childdata.getString("s_name");
                            s_contact = childdata.getString("s_contact");
                            s_area = childdata.getString("s_area_id");
                            s_address = childdata.getString("s_address");

                            s_image = childdata.getString("old_image");
                            s_image_path = childdata.getString("s_image");

                            txt_name.setText(s_name);
                            txt_contact.setText(s_contact);
                            txt_area.setText(s_area);
                            txt_address.setText(s_address);



                            try
                            {
                                Picasso.with(Profile.this).load(childdata.getString("s_image")).fit().centerCrop()
                                        .into(img_profile);
                            }catch (Exception e)
                            {

                            }
                        }


                    }
                    else
                    {
                        String message = parentobject.getString("message");
                        Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("s_id",s_id);
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_profile)
        {
            Intent i1 = new Intent(Profile.this,ChangeProfile.class);
            i1.putExtra("s_name",s_name);
            i1.putExtra("s_contact",s_contact);
            i1.putExtra("s_address",s_address);
            i1.putExtra("s_area_id",s_area);
            i1.putExtra("old_image",s_image);
            i1.putExtra("s_image",s_image_path);
            startActivity(i1);
        }
        if (id == R.id.change_password)
        {
            Intent i1 = new Intent(Profile.this,ChangePassword.class);
            startActivity(i1);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed()
    { }
}