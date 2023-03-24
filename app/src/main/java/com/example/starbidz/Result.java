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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iss.starbidz.AdapterViewHolder.MyRecyclerAdapter6;
import com.iss.starbidz.Connection.ServerConfig;
import com.iss.starbidz.Datastore.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result extends AppCompatActivity
{
    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter6 adapter;

    String s_id;
    Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
        s_id = Data_shpref.data1.getString("s_id",null);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        result(s_id);
    }




    private void result(final String s_id)
    {
        loadingDialog = ProgressDialog.show(Result.this, "Please wait", "Loading...");
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.S_RESULT, new Response.Listener<String>() {
            public SharedPreferences.Editor data1;

            @Override
            public void onResponse(String response) {
                Log.d("fgh", response.toString());


                try {
                    JSONObject parentobject = new JSONObject(response.toString());

                    String status = parentobject.getString("status");
                    if (status.equals("success")){


                        adapter = new MyRecyclerAdapter6(Result.this, feedItemList);
                        mRecyclerView.setAdapter(adapter);

                        JSONArray parentarray = parentobject.getJSONArray("data");

                        for (int i = 0; i<parentarray.length();i++){

                            JSONObject childdata = parentarray.getJSONObject(i);

                            FeedItem feedItem = new FeedItem();
                            feedItem.setResult_image(childdata.getString("result_image"));
                            feedItem.setResult_cat_name(childdata.getString("result_cat_name"));
                            feedItem.setResult_pro_title(childdata.getString("result_pro_title"));
                            feedItem.setResult_final_price(childdata.getString("result_final_price"));
                            feedItem.setB_name(childdata.getString("b_name"));

                            feedItemList.add(feedItem);
                        }

                    }else{
                        String message = parentobject.getString("message");
                        Toast.makeText(Result.this, message, Toast.LENGTH_SHORT).show();
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
    public void onBackPressed()
    { }


}