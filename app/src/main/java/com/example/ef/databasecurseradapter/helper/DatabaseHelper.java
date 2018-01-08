package com.example.ef.databasecurseradapter.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by EF on 13-Nov-17.
 */

public class DatabaseHelper {
    public static final String TAG = DatabaseHelper.class.getSimpleName();

    //Database Configuration
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydatabase.db";

    //table configuration
    public static final String TABLE_NAME = "notes_table";
    public static final String NOTES_TABLE_COLUMN_ID = "_id";
    public static final String NOTES_TABLE_COLUMN_TIME = "time";
    public static final String NOTES_TABLE_COLUMN_NOTES = "notes";

    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;


    public DatabaseHelper(Context context) {
        openHelper = new DatabaseOpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public void insertData(String time, String notes){
        //ContentValues to avoid sql format error
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TABLE_COLUMN_TIME, time);
        contentValues.put(NOTES_TABLE_COLUMN_NOTES, notes);

        database.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData(){
        String buildSQL = " SELECT * FROM " + TABLE_NAME;
        Log.d(TAG, "getAllData SQL: " + buildSQL);
        return database.rawQuery(buildSQL, null);
    }

    public void deleteItem(String _id){
        database.delete(TABLE_NAME, NOTES_TABLE_COLUMN_ID + "=?", new String[]{_id});
    }

    public boolean updateData(String _id, String time, String notes){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TABLE_COLUMN_ID, _id);
        contentValues.put(NOTES_TABLE_COLUMN_TIME, time);
        contentValues.put(NOTES_TABLE_COLUMN_NOTES, notes);

        int i = database.update(TABLE_NAME, contentValues, NOTES_TABLE_COLUMN_ID + "=" + _id, null);
        return i>0;
    }

}
