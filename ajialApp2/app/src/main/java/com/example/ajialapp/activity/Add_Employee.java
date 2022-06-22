package com.example.ajialapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ajialapp.R;
import com.example.ajialapp.DB.DBHelper;

public class Add_Employee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__employee);
        final DBHelper dbHelper = new DBHelper(this);

        final EditText AddId = findViewById(R.id.add_id);
        final EditText AddName = findViewById(R.id.add_name);
        final EditText AddTitle = findViewById(R.id.add_title);
        final EditText AddPassword = findViewById(R.id.add_password);

        final Button btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id =AddId.getText().toString();
                String name =AddName.getText().toString();
                String title =AddTitle.getText().toString();
                String password =AddPassword.getText().toString();

                if(!id.equals("")&& !name.equals("")&&!title.equals("")&&!password.equals("")){
                    if (dbHelper.insertEmployee(id, name, title, password)) {
                        Toast.makeText(getApplicationContext(), "add successfully", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "add failed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
