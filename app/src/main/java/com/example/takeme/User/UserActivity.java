package com.example.takeme.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.takeme.Driver.Adapter.DriverAdapter;
import com.example.takeme.Driver.MainDriverActivity;
import com.example.takeme.Driver.Modle.Driver;
import com.example.takeme.Notifications.DataNotification;
import com.example.takeme.Notifications.Sender;
import com.example.takeme.R;
import com.example.takeme.Splash_Activity;
import com.example.takeme.User.Modle.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.takeme.R.id;
import static com.example.takeme.R.layout;

public class UserActivity extends AppCompatActivity implements DialogAdd.DialogListener {
    int preSelect = -1;

    private RequestQueue requestQueue;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String phoneUser = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
    private CollectionReference reference = db.collection("Journey");
    private DriverAdapter adapter;

    Driver model = new Driver();



    private String point;
    private String TokenR;
    SharedPreferences sp ;
    SharedPreferences.Editor editor;

    private static final String TOPIC_POST_NOTIFICATION = "POST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.logout);
        actionBar.setTitle("Take Me");
        setContentView(layout.activity_user);
        final ArrayList<Driver> drivers = new ArrayList<>();

        sp = getSharedPreferences("notification_SP",MODE_PRIVATE);

        final Button button = findViewById(id.BtnBook);

        requestQueue = Volley.newRequestQueue(getApplicationContext());



        firebaseFirestore.collection("Journey").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Log.d("ttt","Error :"+e.getMessage() );
                }

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED ){
                        Driver driver = doc.getDocument().toObject(Driver.class);
                        drivers.add(driver);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });




        final ListView listView = findViewById(id.listV);
        adapter = new DriverAdapter(drivers,this);
        adapter.notifyDataSetChanged();
        listView.setDivider(null);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 model = drivers.get(position);
                 model.setSelect(true);
                 drivers.set(position,model);
                 point = model.getPointOfStart();
                 TokenR= model.getToken();

                 if (preSelect > -1){
                     Driver userModel = drivers.get(preSelect);
                     userModel.setSelect(false);
                     drivers.set(preSelect,userModel);
                 }
                 preSelect = position;

                 button.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         openDialog();


                     }
                 });




                adapter.updateRecords(drivers);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic(""+TOPIC_POST_NOTIFICATION)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        String msg = "تم اضافة مشوار جديد";
                        if (!task.isSuccessful()){
                            msg = "ام تتم الاضافة ";
                        }


                    }
                });


    }

    public void openDialog(){
        DialogAdd dialogAdd =new DialogAdd();
        dialogAdd.show(getSupportFragmentManager(),"Dialog add");
    }

    @Override
    public void apply(String Name, String Description) {

        final String name = Name ;
        String description = Description ;

        if(!name.equals("") && !description.equals("")){


            final User user = new User(name,description,phoneUser,point);

            reference.document(model.getId()).collection("order").document(phoneUser).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    DataNotification data = new DataNotification(""+name,
                            "هناك طلب",
                            "لقد طلب " + name + " رحلة جديدة",
                            "orderNotification",
                            R.drawable.logo);
                    Sender sender = new Sender(data,TokenR);
                    try {
                        JSONObject senderJason = new JSONObject(new Gson().toJson(sender));
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", senderJason,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> headers = new HashMap<>();
                                headers.put("Content-Type","application/json");
                                headers.put("Authorization","key=AAAA8PKqLx4:APA91bEKdrjpDcZpJOLmhmzwR7DEcu7IU0YX7r82uxCboXit-Z7pPJEbShqYALZ_yMIioQm-yMT0_LWsmpGx3z1r-rVVGwux1LjkoCEWKaAgUKsoelGQDJ84nHFO6Cs4-qrZfzfsJeHR");
                                return headers;
                            }
                        };
                        requestQueue.add(jsonObjectRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }else {
            Toast.makeText(this, "عذرا لم يتم حجز الرحلة لعدم اضافة البيانات", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showPopup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(UserActivity.this);
        alert.setMessage("هل انت متاكد انك تريد الخروج ؟")
                .setPositiveButton("خروج", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                }).setNegativeButton("الغاء", null);
        AlertDialog alert1 = alert.create();
        alert1.show();
    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(UserActivity.this, Splash_Activity.class));
        finish();
    }
    }



