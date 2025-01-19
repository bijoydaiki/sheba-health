package com.riad.shebahealthcheck;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Record_Page extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;
    private BloodSugarAdapter adapter;
    private ArrayList<BloodSugar> bloodSugarList;
    private int selectedId; // To hold the id of the record selected for update or delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        bloodSugarList = new ArrayList<>();
        adapter = new BloodSugarAdapter(this, bloodSugarList);
        listView.setAdapter(adapter);

        // Load records into the list
        loadBloodSugarRecords();

        // Floating Action Button (FAB) to add new record
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddRecordDialog();
            }
        });

        // Set item click listener for updating and deleting records
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BloodSugar bloodSugar = (BloodSugar) parent.getItemAtPosition(position);
                selectedId = bloodSugar.getId();
                showRecordOptionsDialog();
            }
        });
    }

    // Load records into the list
    private void loadBloodSugarRecords() {
        Cursor cursor = dbHelper.getAllBloodSugarRecords();
        bloodSugarList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            float bloodSugar = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_BLOODSUGAR_LEVEL));
            bloodSugarList.add(new BloodSugar(id, date, bloodSugar));
        }
        adapter.notifyDataSetChanged();
    }

    // Show dialog for adding a new record
    private void showAddRecordDialog() {
        final EditText editDate = new EditText(this);
        final EditText editBloodSugar = new EditText(this);

        // Set input type for blood sugar level
        editBloodSugar.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);

        new AlertDialog.Builder(this)
                .setTitle("Add New Record")
                .setMessage("Enter the date and blood sugar level:")
                .setView(editDate)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String date = editDate.getText().toString();
                        float bloodSugar = Float.parseFloat(editBloodSugar.getText().toString());
                        dbHelper.addBloodSugarRecord(date, bloodSugar);
                        loadBloodSugarRecords(); // Reload the records
                        Toast.makeText(Record_Page.this, "Record Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Show options dialog (Update/Delete) when a record is clicked
    private void showRecordOptionsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Record Options")
                .setItems(new CharSequence[]{"Update", "Delete"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            showUpdateRecordDialog();
                        } else if (which == 1) {
                            showDeleteRecordDialog();
                        }
                    }
                })
                .show();
    }

    // Show dialog to update a record
    private void showUpdateRecordDialog() {
        Cursor cursor = dbHelper.getBloodSugarRecordById(selectedId);
        if (cursor.moveToFirst()) {
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            float bloodSugar = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_BLOODSUGAR_LEVEL));

            final EditText editDate = new EditText(this);
            final EditText editBloodSugar = new EditText(this);

            editDate.setText(date);
            editBloodSugar.setText(String.valueOf(bloodSugar));

            // Set input type for blood sugar level
            editBloodSugar.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);

            new AlertDialog.Builder(this)
                    .setTitle("Update Record")
                    .setMessage("Update the date and blood sugar level:")
                    .setView(editDate)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String updatedDate = editDate.getText().toString();
                            float updatedBloodSugar = Float.parseFloat(editBloodSugar.getText().toString());
                            dbHelper.updateBloodSugarRecord(selectedId, updatedDate, updatedBloodSugar);
                            loadBloodSugarRecords(); // Reload the records
                            Toast.makeText(Record_Page.this, "Record Updated", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    // Show dialog to delete a record
    private void showDeleteRecordDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Record")
                .setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteBloodSugarRecord(selectedId);
                        loadBloodSugarRecords(); // Reload the records
                        Toast.makeText(Record_Page.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
