package com.example.ajialapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ajialapp.Adapter.EmpAdapter;
import com.example.ajialapp.DB.DBHelper;
import com.example.ajialapp.modle.Employee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.ajialapp.R;
public class MangeEmployee extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mange_employee);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getItem();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Add Employee", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MangeEmployee.this, Add_Employee.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getItem();
    }

    public void getItem (){
        DBHelper dbHelper = new DBHelper(this);


        recyclerView =findViewById(R.id.recycle);

        EmpAdapter empAdapter = new EmpAdapter(dbHelper.getEmployee(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(empAdapter);
    }

}
