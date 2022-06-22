package com.example.ajialapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ajialapp.R;
import com.example.ajialapp.activity.FirstActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText user = findViewById(R.id.edUser);
        final EditText password = findViewById(R.id.edPassword);
        Button button = findViewById(R.id.btnLogin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User = user.getText().toString();
                String Password = password.getText().toString();

                if(User.equals("admin")&&Password.equals("admin")){
                    Intent intent=new Intent(getApplicationContext(), FirstActivity.class);
                    startActivity(intent);
                }else if(User.equals("")&&Password.equals("")){
                    Toast.makeText(getApplicationContext(),"ادخل البيانات صحيحة",Toast.LENGTH_SHORT).show();
                }else if(!User.equals("admin")||!Password.equals("admin")){
                    Toast.makeText(getApplicationContext(),"ادخل البيانات صحيحة",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
