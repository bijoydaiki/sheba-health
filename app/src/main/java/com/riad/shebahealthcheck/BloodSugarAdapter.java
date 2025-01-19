package com.riad.shebahealthcheck;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BloodSugarAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BloodSugar> bloodSugarList;

    public BloodSugarAdapter(Context context, ArrayList<BloodSugar> bloodSugarList) {
        this.context = context;
        this.bloodSugarList = bloodSugarList;
    }

    @Override
    public int getCount() {
        return bloodSugarList.size();
    }

    @Override
    public Object getItem(int position) {
        return bloodSugarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bloodSugarList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        BloodSugar bloodSugar = bloodSugarList.get(position);
        TextView txtDate = convertView.findViewById(R.id.txtDate);
        TextView txtBloodSugar = convertView.findViewById(R.id.txtBloodSugar);

        txtDate.setText(bloodSugar.getDate());
        txtBloodSugar.setText(String.valueOf(bloodSugar.getBloodSugarLevel()));

        return convertView;
    }
}
