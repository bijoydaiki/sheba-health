package com.riad.shebahealthcheck;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "blood_glucose.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_GLUCOSE = "glucose_records";

    // Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BLOOD_SUGAR = "blood_sugar";
    private static final String COLUMN_DATETIME = "datetime";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_GLUCOSE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_BLOOD_SUGAR + " INTEGER,"
                + COLUMN_DATETIME + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLUCOSE);
        onCreate(db);
    }

    // Create operation
    public long insertRecord(GlucoseRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, record.getName());
        values.put(COLUMN_BLOOD_SUGAR, record.getBloodSugar());
        values.put(COLUMN_DATETIME, record.getDateTime());
        long id = db.insert(TABLE_GLUCOSE, null, values);
        db.close();
        return id;
    }

    // Read operation - get all records
    public List<GlucoseRecord> getAllRecords() {
        List<GlucoseRecord> records = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_GLUCOSE + " ORDER BY " + COLUMN_DATETIME + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                GlucoseRecord record = new GlucoseRecord();
                record.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                record.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                record.setBloodSugar(cursor.getInt(cursor.getColumnIndex(COLUMN_BLOOD_SUGAR)));
                record.setDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_DATETIME)));
                records.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return records;
    }

    // Read operation - get single record
    public GlucoseRecord getRecord(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GLUCOSE,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_BLOOD_SUGAR, COLUMN_DATETIME},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        GlucoseRecord record = new GlucoseRecord();
        record.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        record.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        record.setBloodSugar(cursor.getInt(cursor.getColumnIndex(COLUMN_BLOOD_SUGAR)));
        record.setDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_DATETIME)));

        cursor.close();
        db.close();
        return record;
    }

    // Update operation
    public int updateRecord(GlucoseRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, record.getName());
        values.put(COLUMN_BLOOD_SUGAR, record.getBloodSugar());
        values.put(COLUMN_DATETIME, record.getDateTime());

        int result = db.update(TABLE_GLUCOSE, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    // Delete operation
    public void deleteRecord(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GLUCOSE, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}