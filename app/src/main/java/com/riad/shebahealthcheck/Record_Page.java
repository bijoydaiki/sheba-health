package com.riad.shebahealthcheck;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Record_Page extends AppCompatActivity {
    private EditText nameInput;
    private EditText bloodSugarInput;
    private EditText dateTimeInput;
    private Button addButton;
    private ListView recordsList;
    private DatabaseHelper dbHelper;
    private GlucoseRecordAdapter adapter;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        bloodSugarInput = findViewById(R.id.bloodSugarInput);
        dateTimeInput = findViewById(R.id.dateTimeInput);
        addButton = findViewById(R.id.addButton);
        recordsList = findViewById(R.id.recordsList);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);
        calendar = Calendar.getInstance();

        // Set up date/time picker
        dateTimeInput.setOnClickListener(v -> showDateTimePicker());

        // Set up add button
        addButton.setOnClickListener(v -> addRecord());

        // Load and display records
        loadRecords();
    }

    private void showDateTimePicker() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                dateTimeInput.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void addRecord() {
        String name = nameInput.getText().toString().trim();
        String bloodSugarStr = bloodSugarInput.getText().toString().trim();
        String dateTime = dateTimeInput.getText().toString().trim();

        if (name.isEmpty() || bloodSugarStr.isEmpty() || dateTime.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int bloodSugar = Integer.parseInt(bloodSugarStr);
        GlucoseRecord record = new GlucoseRecord(name, bloodSugar, dateTime);

        long id = dbHelper.insertRecord(record);
        if (id != -1) {
            Toast.makeText(this, "Record added successfully", Toast.LENGTH_SHORT).show();
            clearInputs();
            loadRecords();
        } else {
            Toast.makeText(this, "Error adding record", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRecords() {
        List<GlucoseRecord> records = dbHelper.getAllRecords();
        adapter = new GlucoseRecordAdapter(this, records, dbHelper);
        recordsList.setAdapter(adapter);
    }

    private void clearInputs() {
        nameInput.setText("");
        bloodSugarInput.setText("");
        dateTimeInput.setText("");
    }
}
