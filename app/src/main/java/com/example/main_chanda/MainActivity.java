package com.example.main_chanda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextMobile;
    private EditText etfname;
    private EditText etlname;
    private Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        etfname=findViewById(R.id.fname);
        etlname=findViewById(R.id.lname);
        btnsubmit=findViewById(R.id.verify_otp);
        editTextMobile = findViewById(R.id.mobile);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = editTextMobile.getText().toString().trim();



                if(mobile.isEmpty() || mobile.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                }
                else{
                    Intent intent=new Intent(getApplicationContext(),Login_OTP.class);
                    intent.putExtra("fname",etfname.getText().toString());
                    intent.putExtra("lname",etlname.getText().toString());
                    intent.putExtra("mobile",editTextMobile.getText().toString());
                    startActivity(intent);

                }


            }
        });

    }
    }

