package com.riad.shebahealthcheck;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blood_sugar.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_BLOODSUGAR = "blood_sugar";

    // Column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_BLOODSUGAR_LEVEL = "blood_sugar_level";

    // SQL to create table
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_BLOODSUGAR + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_BLOODSUGAR_LEVEL + " REAL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOODSUGAR);
        onCreate(db);
    }

    // Add a new blood sugar record
    public long addBloodSugarRecord(String date, float bloodSugarLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_BLOODSUGAR_LEVEL, bloodSugarLevel);
        return db.insert(TABLE_BLOODSUGAR, null, values);
    }

    // Get all blood sugar records
    public Cursor getAllBloodSugarRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BLOODSUGAR, null, null, null, null, null, COLUMN_DATE + " DESC");
    }

    // Get a specific blood sugar record by ID
    public Cursor getBloodSugarRecordById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BLOODSUGAR, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
    }

    // Update a blood sugar record
    public int updateBloodSugarRecord(int id, String date, float bloodSugarLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_BLOODSUGAR_LEVEL, bloodSugarLevel);
        return db.update(TABLE_BLOODSUGAR, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Delete a blood sugar record
    public int deleteBloodSugarRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BLOODSUGAR, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
