package com.example.starbidz;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class B_myAuction extends AppCompatActivity
{
//    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();

    private RecyclerView mRecyclerView;
//    private MyRecyclerAdapter4 adapter;

    String b_id;
    Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_my_auction);

        Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
        b_id = Data_shpref.data1.getString("b_id",null);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        myauction(b_id);

    }

    private void myauction(final String b_id)
    {
        loadingDialog = ProgressDialog.show(B_myAuction.this, "Please wait", "Loading...");
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.B_MY_AUCTION, new Response.Listener<String>() {
            public SharedPreferences.Editor data1;

            @Override
            public void onResponse(String response) {
                Log.d("fgh", response.toString());


                try {
                    JSONObject parentobject = new JSONObject(response.toString());

                    String status = parentobject.getString("status");
                    if (status.equals("success")){


//                        adapter = new MyRecyclerAdapter4(B_myAuction.this, feedItemList);
//                        mRecyclerView.setAdapter(adapter);

                        JSONArray parentarray = parentobject.getJSONArray("data");

                        for (int i = 0; i<parentarray.length();i++){

                            JSONObject childdata = parentarray.getJSONObject(i);

//                            FeedItem feedItem = new FeedItem();
//                            feedItem.setA_pro_image(childdata.getString("a_pro_image"));
//                            feedItem.setA_cat_name(childdata.getString("a_cat_name"));
//                            feedItem.setA_pro_title(childdata.getString("a_pro_title"));
//                            feedItem.setA_pro_price(childdata.getString("a_pro_price"));
//                            feedItem.setA_auc_price(childdata.getString("a_auc_price"));
//                            feedItem.setA_auc_limit(childdata.getString("a_auc_limit"));
//                            feedItem.setS_name(childdata.getString("s_name"));

//                            feedItemList.add(feedItem);
                        }

                    }else{
                        String message = parentobject.getString("message");
                        Toast.makeText(B_myAuction.this, message, Toast.LENGTH_SHORT).show();
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

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("b_id",b_id);

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