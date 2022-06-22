package com.example.takeme.User.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.takeme.R;
import com.example.takeme.User.Modle.User;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    ArrayList<User> data;
    Activity activity;
    LayoutInflater layoutInflater;

    public UserAdapter(ArrayList<User> users , Activity activity){
        this.data=users;
        this.activity=activity;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public UserAdapter(){}
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
            root = layoutInflater.inflate(R.layout.item_c,null);
        }

        TextView name = root.findViewById(R.id.NameUserI);
        TextView pointOfStart = root.findViewById(R.id.PointStartC);
        TextView PhoneNumber = root.findViewById(R.id.phone);
        ConstraintLayout constraintLayout = root.findViewById(R.id.ConstraintLayout2);
        ImageView imageView = root.findViewById(R.id.cc);

        imageView.setImageResource(R.drawable.ic_car_);
        name.setText(data.get(position).getName());
        pointOfStart.setText(data.get(position).getPointStart());
        PhoneNumber.setText(data.get(position).getPhoneNumber());

        return root;


    }

    public void updateRecords (ArrayList<User> users){
        this.data = users;
        notifyDataSetChanged();
    }
}
