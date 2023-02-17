package com.example.starbidz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class B_verifyOTP extends AppCompatActivity
{
    EditText edt_otp;
    Button btn_verify;
    TextView txt_desc;

    String s_contact,otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_verify_otp);

        Intent i1 = getIntent();
        s_contact = i1.getStringExtra("b_contact");
        otp = i1.getStringExtra("otp");

        txt_desc = (TextView)findViewById(R.id.txt_desc);
        txt_desc.setText("Please type the Verification code send to +91-"+s_contact);

        edt_otp = (EditText)findViewById(R.id.edt_otp);

        btn_verify = (Button)findViewById(R.id.btn_verify);
        btn_verify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String s_otp = edt_otp.getText().toString();

                if (s_otp.length()==0)
                {
                    edt_otp.setError("Enter OTP");
                }
                else
                {
                    if (s_otp.equals(otp))
                    {
//                        Intent i1 = new Intent(B_verifyOTP.this,B_register.class);
//                        i1.putExtra("b_contact",s_contact);
//                        startActivity(i1);

                    }
                    else
                    {
                        Toast.makeText(B_verifyOTP.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    public void onBackPressed()
    { }
}