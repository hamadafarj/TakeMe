package com.example.takeme.Driver;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.takeme.Driver.Modle.Driver;
import com.example.takeme.Fragments.TimePickerFragment;
import com.example.takeme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_JourneyActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText nameDriver ;
    private EditText pointStart ;
    private EditText pointEnd ;
    private EditText time ;
    private EditText phone ;
    private NumberPicker numberPicker = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String user = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    private CollectionReference reference = db.collection("Journey/");
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__journey);

        nameDriver = findViewById(R.id.NameDr);
        pointStart = findViewById(R.id.PointStart);
        pointEnd = findViewById(R.id.pointEnd);
        time = findViewById(R.id.TimeStart);
        phone = findViewById(R.id.PhoneDr);
        Button button = findViewById(R.id.AddJourney);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(3);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                token = task.getResult().getToken();
            }
        });
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time.setText(hourOfDay + ":" + minute);
    }
    private void saveData(){
        final String Name = nameDriver.getText().toString();
        String PointStart = pointStart.getText().toString();
        String PointEnd = pointEnd.getText().toString();
        String Time = time.getText().toString();
        String Phone = phone.getText().toString();
        String Number = String.valueOf(numberPicker.getValue());
        if(!Name.equals("") && !PointStart.equals("") && !PointEnd.equals("") && !Time.equals("")){
            final Driver driver = new Driver(user,Name,PointStart,PointEnd,Number,Time,Phone,token);
            reference.document(user).set(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Add_JourneyActivity.this, "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Add_JourneyActivity.this,MainDriverActivity.class);
                    startActivity(intent);
                }
            });
            finish();
        }
    }
}
