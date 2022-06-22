package com.example.ajialapp.Adapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajialapp.DB.DBHelper;
import com.example.ajialapp.R;
import com.example.ajialapp.modle.Employee;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.MyViewHolder> {
    ArrayList<Employee> data;
    Activity activity;

    DBHelper dbHelper;

    public EmpAdapter(ArrayList<Employee> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.emp_item,viewGroup,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        dbHelper = new DBHelper(activity);


        myViewHolder.id.setText(data.get(i).getId());
        myViewHolder.name.setText(data.get(i).getName());
        myViewHolder.title.setText(data.get(i).getTitle());
        myViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        myViewHolder.delate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            dbHelper.deleteEmployee(data.get(i).getId());
            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id ;
        TextView name ;
        TextView title ;
        Button edit;
        Button delate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.TvId);
            name = itemView.findViewById(R.id.TvName);
            title = itemView.findViewById(R.id.TvTitle);
            edit = itemView.findViewById(R.id.btn_edit);
            delate = itemView.findViewById(R.id.btn_delate);
        }
    }


}
