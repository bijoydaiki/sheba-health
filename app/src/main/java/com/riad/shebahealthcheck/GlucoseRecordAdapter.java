package com.riad.shebahealthcheck;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GlucoseRecordAdapter extends ArrayAdapter<GlucoseRecord> {
    private Context context;
    private List<GlucoseRecord> records;
    private DatabaseHelper dbHelper;

    public GlucoseRecordAdapter(Context context, List<GlucoseRecord> records, DatabaseHelper dbHelper) {
        super(context, R.layout.list_item, records);
        this.context = context;
        this.records = records;
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        GlucoseRecord record = records.get(position);

        TextView nameText = convertView.findViewById(R.id.nameText);
        TextView bloodSugarText = convertView.findViewById(R.id.bloodSugarText);
        TextView dateTimeText = convertView.findViewById(R.id.dateTimeText);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        nameText.setText(record.getName());
        bloodSugarText.setText(String.valueOf(record.getBloodSugar()) + " mg/dL");
        dateTimeText.setText(record.getDateTime());

        deleteButton.setOnClickListener(v -> {
            dbHelper.deleteRecord(record.getId());
            records.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Record deleted", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}