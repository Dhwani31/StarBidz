package com.iss.starbidz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iss.starbidz.Connection.ServerConfig;
import com.mindorks.paracamera.Camera;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Registration extends AppCompatActivity
{
    ImageView img_profile;
    EditText edt_name,edt_contact,edt_address,edt_password,edt_conf_pass;
    String s_name,s_contact,s_area,s_address,s_password,s_conf_password;
    String s_id;

    Spinner sp_id;
    ArrayList<String> listitems = new ArrayList();
    ArrayAdapter<String> adapter;

    Button btn_submit;

    int img_flag = 0;
    Camera camera;
    File sourceFile;
    long totalSize;
    private SharedPreferences.Editor data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //contact
        Intent i1 = getIntent();
        s_contact = i1.getStringExtra("s_contact");

        edt_contact = (EditText)findViewById(R.id.edt_contact);
        edt_contact.setEnabled(false);
        edt_contact.setText(s_contact);

        isStoragePermissionGranted();

        img_profile = (ImageView)findViewById(R.id.img_profile);
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                selectimage();
            }
        });


        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_address = (EditText)findViewById(R.id.edt_address);
        edt_password = (EditText)findViewById(R.id.edt_password);
        edt_conf_pass = (EditText)findViewById(R.id.edt_conf_pass);


        //AREA
        fetcharea();

        sp_id = (Spinner)findViewById(R.id.sp_id);
        adapter = new ArrayAdapter<>(this,R.layout.dropdown,R.id.txt,listitems);
        sp_id.setAdapter(adapter);
        sp_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                s_area = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                s_name = edt_name.getText().toString();
                s_address = edt_address.getText().toString();
                s_password = edt_password.getText().toString();
                s_conf_password = edt_conf_pass.getText().toString();

                if (s_name.length() == 0)
                {
                    edt_name.setError("Enter Name");
                }
                else if (s_address.length() == 0)
                {
                    edt_address.setError("Enter Address");
                }
                else if (img_flag == 0)
                {
                    Toast.makeText(Registration.this, "Profile Picture Not Set", Toast.LENGTH_SHORT).show();
                }
                else if (s_password.length() == 0)
                {
                    edt_password.setError("Enter Password");
                }
                else if (s_conf_password.length() == 0)
                {
                    edt_conf_pass.setError("Enter Confirm Password");
                }
                else if (s_password.equals(s_conf_password))
                {
                    new UploadFileToServer().execute();
                }
                else
                {
                    Toast.makeText(Registration.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void fetcharea()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.FETCH_AREA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("fgh",response.toString());

                        ArrayList<String> list = new ArrayList<>();
                        try
                        {
                            JSONObject patent_object = new JSONObject(response.toString());

                            JSONArray parent_array = patent_object.getJSONArray("data");

                            for (int i = 0;i<parent_array.length();i++)
                            {
                                JSONObject json_data = parent_array.getJSONObject(i);


                                list.add(json_data.getString("area_name"));

                            }

                            listitems.addAll(list);
                            adapter.notifyDataSetChanged();

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("fgh",error.toString());
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void selectimage()
    {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    // Toast.makeText(add_request.this, "Open Camera", Toast.LENGTH_SHORT).show();
                    //  captureImage1();
                    captureuserImage();
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 100);
                }
            }
        });
        builder.show();
    }

    private void captureuserImage()
    {
        camera = new Camera.Builder()
                .setDirectory("upload")
                .setName("seller_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)
                .setTakePhotoRequestCode(101)
                .build(Registration.this);
        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == 100)
            {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage,filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));;


                Log.d("fgh","V1 value - "+picturePath);


                sourceFile = new File(picturePath);
                img_profile.setImageBitmap(thumbnail);
                img_flag = 1;

            }
            if (requestCode == 101)
            {
                String filepath = camera.getCameraBitmapPath();
                Log.d("fgh","Camera Path - "+filepath);
                Bitmap bitmap = camera.getCameraBitmap();

                if (bitmap != null) {
                    img_profile.setImageBitmap(bitmap);
                    sourceFile = new File(filepath);
                    img_flag = 1;

                }
                else
                {
                    Toast.makeText(Registration.this, "Picture not taken!", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }



    private class UploadFileToServer extends AsyncTask<Void, Integer, String>
    {
        Dialog loadingDialog;
        @Override
        protected void onPreExecute()
        {
            // setting progress bar to zero
            //   progressBar.setProgress(0);
            super.onPreExecute();

            loadingDialog = ProgressDialog.show(Registration.this, "Please wait", "Loading...");

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            //   progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            //   progressBar.setProgress(progress[0]);

            // updating percentage value
            // txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            Log.d("fgh", ServerConfig.S_REGISTER);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ServerConfig.S_REGISTER);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                //   File sourceFile = new File(picturePath);
                //  Log.d("fgh",picturePath);


                // Adding file data to http body


                entity.addPart("s_image", new FileBody(sourceFile));

                entity.addPart("s_name", new StringBody(s_name));
                entity.addPart("s_contact", new StringBody(s_contact));
                entity.addPart("s_area_id", new StringBody(s_area));
                entity.addPart("s_address", new StringBody(s_address));
                entity.addPart("s_password", new StringBody(s_password));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("fgh", "Response from server: " + result);

            loadingDialog.dismiss();
            Log.d("fgh",result.toString());
            // showing the server response in an alert dialog


            showAlert(result);

            super.onPostExecute(result);
        }

    }

    private void showAlert(String result)
    {
        Log.d("fgh",result.toString());

        try
        {
            JSONObject parent_object = new JSONObject(result.toString());

            String status = parent_object.getString("status");

            if (status.equals("success"))
            {
                //Toast.makeText(this, "Request Added Succefully", Toast.LENGTH_SHORT).show();

                JSONArray parent_array = parent_object.getJSONArray("data");
                for (int i = 0;i<parent_array.length();i++){

                    JSONObject child_object = parent_array.getJSONObject(i);

                    s_id = child_object.getString("s_id");
                }

                //seller_id
                Data_shpref.data1 = getSharedPreferences(Data_shpref.data,0);
                String data = Data_shpref.data1.getString("s_id",null);

                this.data1 = Data_shpref.data1.edit();
                this.data1.putString("s_id",s_id);
                this.data1.commit();



                String msg = parent_object.getString("message");
                Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();

                Intent i1 = new Intent(Registration.this,MainActivity.class);
                startActivity(i1);
                finish();
            }
            else
            {
                String msg = parent_object.getString("message");
                Toast.makeText(Registration.this, msg, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Request Not Added", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }





    public boolean isStoragePermissionGranted()
    {
        String TAG = "cxvx";
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED ) {
                Log.v(TAG, "Permission is granted");
                return true;
            }
            else
            {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(Registration.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else
        {   //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onBackPressed()
    { }

}