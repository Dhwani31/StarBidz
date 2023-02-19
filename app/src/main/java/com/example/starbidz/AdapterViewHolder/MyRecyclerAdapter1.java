//package com.example.starbidz.AdapterViewHolder;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.graphics.Color;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.iss.starbidz.Connection.ServerConfig;
//import com.iss.starbidz.Datastore.FeedItem;
//import com.iss.starbidz.MyProperty;
//import com.iss.starbidz.R;
//import com.iss.starbidz.ViewHolder.FeedListRowHolder1;
//import com.squareup.picasso.Picasso;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MyRecyclerAdapter1 extends RecyclerView.Adapter<FeedListRowHolder1> {
//
//
//    private List<FeedItem> feedItemList;
//
//    private Context mContext;
//
//    public MyRecyclerAdapter1(Context context, List<FeedItem> feedItemList) {
//        this.feedItemList = feedItemList;
//        this.mContext = context;
//
//    }
//    @Override
//    public FeedListRowHolder1 onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_my_property, null);
//        FeedListRowHolder1 mh = new FeedListRowHolder1(v);
//        return mh;
//    }
//    @Override
//    public void onBindViewHolder(final FeedListRowHolder1 feedListRowHolder1, final int i) {
//        final FeedItem feedItem = feedItemList.get(i);
//
//        feedListRowHolder1.txt_category.setText(feedItem.getPro_cat_id());
//        feedListRowHolder1.txt_title.setText(feedItem.getPro_title());
//        feedListRowHolder1.txt_details.setText(feedItem.getPro_details());
//        feedListRowHolder1.txt_sdate.setText(feedItem.getPro_start_date());
//        feedListRowHolder1.txt_edate.setText(feedItem.getPro_end_date());
//        feedListRowHolder1.txt_price.setText("₹ "+feedItem.getPro_price());
//
//        feedListRowHolder1.txt_status.setText(feedItem.getPro_status());
//
//        Log.d("fgh",feedItem.getPro_image());
//        Picasso.with(mContext)
//                .load(feedItem.getPro_image())
//                .into(feedListRowHolder1.img_product);
//
//        if (feedItem.getPro_status().equals("Inactive"))
//        {
//            feedListRowHolder1.txt_status.setTextColor(Color.parseColor("#ff0000"));//red
//        }
//        else if (feedItem.getPro_status().equals("Active"))
//        {
//            feedListRowHolder1.txt_status.setTextColor(Color.parseColor("#69A821"));//green
//        }
//
//
//        feedListRowHolder1.btn_delete.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                deletepost(feedItem.getPro_id());
//            }
//        });
//
//    }
//
//    private void deletepost(final String pro_id)
//    {
//        final ProgressDialog progressdialog = new ProgressDialog(mContext);
//        progressdialog.setMessage("Please Wait....");
//        progressdialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.S_DELETE_PROPERTY,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("fgh",response.toString());
//                        progressdialog.dismiss();
//                        try {
//                            JSONObject responsedata = new JSONObject(response);
//                            String status = responsedata.getString("status");
//
//                            if (status.equals("success"))
//                            {
//                                ((MyProperty)mContext).proreload();
//
//                            }else{
//                                String message = responsedata.getString("message");
//                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressdialog.dismiss();
//                        Log.d("fgh",error.toString());
//                        Toast.makeText(mContext, "Please Try again Later...!", Toast.LENGTH_LONG).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> parameters = new HashMap<String, String>();
//                parameters.put("pro_id",pro_id);
//
//
//                return parameters;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                500000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//        requestQueue.add(stringRequest);
//    }
//
//    @Override
//    public int getItemCount() {
//        return (null != feedItemList ? feedItemList.size() : 0);
//    }
//
//
//}
