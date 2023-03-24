package com.iss.starbidz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iss.starbidz.AdapterViewHolder.MyRecyclerAdapter2;
import com.iss.starbidz.Connection.ServerConfig;
import com.iss.starbidz.Datastore.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class B_all_property extends AppCompatActivity
{
    Dialog loadingDialog;

    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_all_property);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        all_property();
    }

    private void all_property()
    {
        loadingDialog = ProgressDialog.show(B_all_property.this, "Please wait", "Loading...");
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.B_ALL_PROPERTY, new Response.Listener<String>() {
            public SharedPreferences.Editor data1;

            @Override
            public void onResponse(String response) {
                Log.d("fgh", response.toString());


                try {
                    JSONObject parentobject = new JSONObject(response.toString());

                    String status = parentobject.getString("status");
                    if (status.equals("success")){


                        adapter = new MyRecyclerAdapter2(B_all_property.this, feedItemList);
                        mRecyclerView.setAdapter(adapter);

                        JSONArray parentarray = parentobject.getJSONArray("data");

                        for (int i = 0; i<parentarray.length();i++){

                            JSONObject childdata = parentarray.getJSONObject(i);

                            FeedItem feedItem = new FeedItem();
                            feedItem.setPro_id(childdata.getString("pro_id"));
                            feedItem.setPro_cat_id(childdata.getString("pro_cat_id"));
                            feedItem.setPro_s_id(childdata.getString("pro_s_id"));
                            feedItem.setPro_title(childdata.getString("pro_title"));
                            feedItem.setPro_start_date(childdata.getString("pro_start_date"));
                            feedItem.setPro_end_date(childdata.getString("pro_end_date"));
                            feedItem.setPro_price(childdata.getString("pro_price"));
                            feedItem.setPro_image(childdata.getString("pro_image"));
                            feedItem.setPro_image_old(childdata.getString("pro_image_old"));
                            feedItem.setPro_details(childdata.getString("pro_details"));



                            feedItemList.add(feedItem);

                        }



                    }else{
                        String message = parentobject.getString("message");
                        Toast.makeText(B_all_property.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("fgh",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fgh", error.toString());

            }

        });
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