package com.example.takeme.Driver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.takeme.R;
import com.example.takeme.Splash_Activity;
import com.example.takeme.User.Adapter.UserAdapter;
import com.example.takeme.User.LoginActivity_user;
import com.example.takeme.User.Modle.User;
import com.example.takeme.User.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainDriverActivity extends AppCompatActivity {


    private static final String TOPIC_ORDER_NOTIFICATION = null;


   private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
   private String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.logout);
        actionBar.setTitle("Take Me");
        setContentView(R.layout.activity_main_driver);

        final ArrayList<User> users = new ArrayList<>();

        Button button = findViewById(R.id.BtnAdd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDriverActivity.this,Add_JourneyActivity.class);
                startActivity(intent);
            }
        });


        firebaseFirestore.collection("Journey").document(email).collection("order").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Log.d("ttt","Error :"+e.getMessage() );
                }

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED ){
                        User user= doc.getDocument().toObject(User.class);
                        users.add(user);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });

        final ListView listView = findViewById(R.id.listVD);
        adapter = new UserAdapter(users,this);
        adapter.notifyDataSetChanged();
        listView.setDivider(null);
        listView.setAdapter(adapter);


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
        AlertDialog.Builder alert = new AlertDialog.Builder(MainDriverActivity.this);
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
        startActivity(new Intent(MainDriverActivity.this, Splash_Activity.class));
        finish();
    }

}
