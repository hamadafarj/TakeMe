package com.example.takeme.Driver.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.takeme.Driver.Modle.Driver;
import com.example.takeme.R;

import java.util.ArrayList;

public class DriverAdapter extends BaseAdapter {
    ArrayList<Driver> data;
    Activity activity;
    LayoutInflater layoutInflater;

    public DriverAdapter(ArrayList<Driver> drivers , Activity activity){
        this.data=drivers;
        this.activity=activity;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public  DriverAdapter(){}
    @Override
    public int getCount() { return data.size(); }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = convertView;
        if (root == null){
            root = layoutInflater.inflate(R.layout.item_user,null);
        }

        TextView name = root.findViewById(R.id.NameDriver);
        TextView pointOfStart = root.findViewById(R.id.PointOfStart);
        TextView pointOfEnd = root.findViewById(R.id.PointOfEnd);
        TextView numberOfPe = root.findViewById(R.id.NumberOfPe);
        TextView textView = root.findViewById(R.id.text);
        ConstraintLayout constraintLayout = root.findViewById(R.id.ConstraintLayout);
        TextView time = root.findViewById(R.id.Time);
        ImageView imageView = root.findViewById(R.id.imageView3);
        ImageView imageView1 =root.findViewById(R.id.imageView4);


        imageView.setImageResource(R.drawable.ic_car_);
        textView.setText("إلى");
        name.setText(data.get(position).getName());
        pointOfStart.setText(data.get(position).getPointOfStart());
        pointOfEnd.setText(data.get(position).getPointOfEnd());
        numberOfPe.setText(data.get(position).getNumber());
        time.setText(data.get(position).getTime());
        if(data.get(position).isSelect()){
            imageView1.setImageResource(R.drawable.ic_check_circle_black2_24dp);
            constraintLayout.setBackgroundResource(R.drawable.item_select);
        }else {
            imageView1.setImageResource(R.drawable.ic_check_circle_black_24dp);
            constraintLayout.setBackgroundResource(R.drawable.item_not_select);
        }
        return root;


    }

    public void updateRecords (ArrayList<Driver> driver){
        this.data = driver;
        notifyDataSetChanged();
    }
}
