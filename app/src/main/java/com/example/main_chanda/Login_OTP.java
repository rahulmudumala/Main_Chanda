package com.example.main_chanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class Login_OTP extends AppCompatActivity {
    public String VerificationId;
    private EditText etotp;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__o_t_p);
        final String mobile=getIntent().getStringExtra("mobile");
        sendVerificationCode(mobile);
        etotp=findViewById(R.id.setotp);

        findViewById(R.id.btn_otp_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = etotp.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    etotp.setError("Enter valid code");
                    etotp.requestFocus();
                }
                else{
                verifyCode(code);
                }

            }
        });

    }
    public void verifyCode(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(VerificationId,code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),Main_page.class));
                            finish();
                        }
                    }
                });
    }
    public void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                30,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    VerificationId=s;
                    Toast.makeText(getApplicationContext(),"OTP sent",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    VerificationId=phoneAuthCredential.getSmsCode();

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    // new MainActivity().setToast(message);
                }
            };

}
