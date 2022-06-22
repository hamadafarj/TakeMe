package com.example.takeme.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.takeme.Driver.Login_driverActivity;
import com.example.takeme.Driver.MainDriverActivity;
import com.example.takeme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity_user extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private String PhoneNumber;

    ProgressDialog Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button button = findViewById(R.id.BtnLoginDr);
        final Button button2 = findViewById(R.id.BtnLoginConfirm);
        final TextView loginDr = findViewById(R.id.loginDr);
        final TextView textView = findViewById(R.id.editText2);
        final TextView textView2= findViewById(R.id.textView3);
        final EditText mobile = findViewById(R.id.mobile);
        final EditText confirmMobile = findViewById(R.id.confirm_mobile);


        mAuth = FirebaseAuth.getInstance();
        Loading = new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumber = mobile.getText().toString();
                if (TextUtils.isEmpty(PhoneNumber)) {

                    Toast.makeText(LoginActivity_user.this, "أدخل رقم الجوال بشكل صحيح ", Toast.LENGTH_SHORT).show();

                } else {
                    Loading.setTitle("رمز التفعيل");
                    Loading.setMessage("جاري ارسال رمز التفعيل ");
                    Loading.setCanceledOnTouchOutside(false);
                    Loading.show();

                    mobile.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    textView2.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);


                    button2.setVisibility(View.VISIBLE);
                    confirmMobile.setVisibility(View.VISIBLE);


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+970" + PhoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            LoginActivity_user.this,               // Activity (for callback binding)
                            mCallbacks);



                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = confirmMobile.getText().toString();
                if (TextUtils.isEmpty(code) || code.length() < 6) {
                    Toast.makeText(LoginActivity_user.this, "enter the code you hae it", Toast.LENGTH_SHORT).show();
                    Loading.dismiss();
                } else {

                    Loading.setTitle("رمز التفعيل");
                    Loading.setMessage("جاري التأكد من صحة الرمز المدخل ");
                    Loading.setCanceledOnTouchOutside(false);
                    Loading.show();
                    verifyCode(code);
                }
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    verifyCode(code);

                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Loading.dismiss();
                Log.e("ttt", e.getLocalizedMessage());
                Log.e("ttt", e.getMessage());

                Toast.makeText(LoginActivity_user.this, "الرقم المدخل غير صحيح او محظور لفترة قصيرة", Toast.LENGTH_SHORT).show();

                mobile.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);


                button2.setVisibility(View.GONE);
                confirmMobile.setVisibility(View.GONE);

            }

            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                mVerificationId = verificationId;
                mResendToken = token;

                Loading.dismiss();
                Toast.makeText(LoginActivity_user.this, "تم ارسال الكود ..", Toast.LENGTH_SHORT).show();

                mobile.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                button.setVisibility(View.GONE);


                button2.setVisibility(View.VISIBLE);
                confirmMobile.setVisibility(View.VISIBLE);

            }


        };

        loginDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity_user.this, Login_driverActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Loading.dismiss();
                            SendUSerTotTheNextActivity();

                        } else {
                            String massage = task.getException().toString();
                            Toast.makeText(LoginActivity_user.this, "رمز التفيعل المدخل غير صحيح يرجى ادخال الرمز بشكل صحيح", Toast.LENGTH_SHORT).show();
                            Loading.dismiss();
                        }
                    }

                });
    }

    private void SendUSerTotTheNextActivity() {
        Intent intent = new Intent(LoginActivity_user.this, UserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser()!= null) {
            int n = 0;
            if (FirebaseAuth.getInstance().getCurrentUser().getEmail().isEmpty()) {
                n = 2;
            }
            if (!FirebaseAuth.getInstance().getCurrentUser().getEmail().isEmpty()){
                n = 1;
            }
            switch (n){
                case 1:
                    Intent intent = new Intent(LoginActivity_user.this, MainDriverActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                case 2:
                    Intent intent1 = new Intent(LoginActivity_user.this, UserActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                    break;
            }
        }

    }
}
