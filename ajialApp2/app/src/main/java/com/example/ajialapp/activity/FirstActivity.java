package com.example.ajialapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ajialapp.R;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button mangeEmp = findViewById(R.id.btn_mange_emp);
        Button mangeholiday = findViewById(R.id.btn_mange_holiday);
        Button mangeHolidayEmp = findViewById(R.id.btn_mange_holiday_emp);
        Button mange = findViewById(R.id.btn_mange_);


        mangeEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MangeEmployee.class);
                startActivity(intent);
            }
        });
    }
}
