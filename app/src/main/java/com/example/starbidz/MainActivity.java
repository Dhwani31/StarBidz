package com.example.starbidz;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
//import com.example.starbidz.AdapterViewHolder.MyRecyclerAdapter7;
import com.example.starbidz.Connection.ServerConfig;
//import com.example.starbidz.Datastore.FeedItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    Dialog loadingDialog;
    String s_id;

//    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();
    private RecyclerView mRecyclerView;
//    private MyRecyclerAdapter7 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//               Intent i1 = new Intent(MainActivity.this,AddProperty.class);
//               startActivity(i1);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
//                R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
        s_id = Data_shpref.data1.getString("s_id",null);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        auction(s_id);



//        navigationView.bringToFront();
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
//        {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
//            {
//                switch (menuItem.getItemId())
//                {
//                    case R.id.nav_home:
//                        Intent i1 = new Intent(MainActivity.this,MainActivity.class);
//                        startActivity(i1);
//                        break;
//                    case R.id.nav_profile:
//                        Intent i2 = new Intent(MainActivity.this,Profile.class);
//                        startActivity(i2);
//                        break;
//                    case R.id.nav_my_pro:
//                        Intent i3 = new Intent(MainActivity.this,MyProperty.class);
//                        startActivity(i3);
//                        break;
//                    case R.id.nav_auction:
//                        Intent i4 = new Intent(MainActivity.this, Auction.class);
//                        startActivity(i4);
//                        break;
//                    case R.id.nav_result:
//                        Intent i5 = new Intent(MainActivity.this,Result.class);
//                        startActivity(i5);
//                        break;
//                    case R.id.nav_feedback:
//                        Intent i6 = new Intent(MainActivity.this,Feedback.class);
//                        startActivity(i6);
//                        break;
//                    case R.id.nav_share:
//                        Intent share_intent = new Intent();
//                        share_intent.setAction(Intent.ACTION_SEND);
//                        share_intent.setType("text/plain");
//                        share_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        share_intent.putExtra(Intent.EXTRA_TEXT,
//                                "Refer StarBidz Application for more details");
//                        try {
//                            startActivity(Intent.createChooser(share_intent,
//                                    "ShareThroughChooser"));
//                        } catch (android.content.ActivityNotFoundException ex) {
//                            (new AlertDialog.Builder(MainActivity.this)
//                                    .setMessage("Share failed")
//                                    .setPositiveButton("OK",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog,
//                                                                    int whichButton) {
//                                                }
//                                            }).create()).show();
//                        }
//                        break;
//                    case R.id.nav_logout:
//                        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
//                        builder.setCancelable(false)
//                                .setMessage("Are you sure want to Logout?")
//
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                                {
//                                    public SharedPreferences.Editor data1;
//
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i)
//                                    {
//
//                                        Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
//                                        String data = Data_shpref.data1.getString("s_id",null);
//
//                                        this.data1 = Data_shpref.data1.edit();
//                                        this.data1.putString("s_id",null);
//                                        this.data1.commit();
//
//
//                                        Intent i1 = new Intent(MainActivity.this,SelectType.class);
//                                        startActivity(i1);
//                                        finish();
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    }
//                                });
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.setTitle("Logout");
//                        alertDialog.show();
//                }
//
//                return false;
//            }
//        });



    }



    private void auction(final String s_id)
    {
        loadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
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


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.S_AUCTION, new Response.Listener<String>() {
            public SharedPreferences.Editor data1;

            @Override
            public void onResponse(String response) {
                Log.d("fgh", response.toString());


                try {
                    JSONObject parentobject = new JSONObject(response.toString());

                    String status = parentobject.getString("status");
                    if (status.equals("success")){


//                        adapter = new MyRecyclerAdapter7(MainActivity.this, feedItemList);
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
//                            feedItem.setB_name(childdata.getString("b_name"));
//
//                            feedItemList.add(feedItem);
                        }

                    }else{
                        String message = parentobject.getString("message");
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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




    //ABOUT
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        if (id == R.id.action_settings)
        {
//            Intent i1 = new Intent(MainActivity.this,About.class);
//            startActivity(i1);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}