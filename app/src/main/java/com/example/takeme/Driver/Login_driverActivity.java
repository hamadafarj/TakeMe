package com.example.takeme.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.takeme.R;
import com.example.takeme.User.LoginActivity_user;
import com.example.takeme.User.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_driverActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Log In");
        setContentView(R.layout.activity_login_driver);
        firebaseAuth = FirebaseAuth.getInstance();

        Button button = findViewById(R.id.BtnLoginDr);
        final TextView btnRegister = findViewById(R.id.btn_register);
        final EditText email = findViewById(R.id.EdEmail);
        final EditText passWord = findViewById(R.id.EdPassword);
        progressBar = findViewById(R.id.Progress);
        progressBar.setVisibility(View.GONE);

        button.setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(passWord.getText().toString())) {
                Toast.makeText(Login_driverActivity.this, "يرجى إدخال البيانات ", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), passWord.getText().toString())
                        .addOnCompleteListener(task -> {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login_driverActivity.this, MainDriverActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login_driverActivity.this, "يرجى التأكد من صحة البيانات", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_driverActivity.this, Signup_Driver_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!= null){
            Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login_driverActivity.this, MainDriverActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }
}
