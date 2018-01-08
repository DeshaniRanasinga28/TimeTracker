package com.example.ef.databasecurseradapter.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.ef.databasecurseradapter.helper.DatabaseHelper.DATABASE_NAME;
import static com.example.ef.databasecurseradapter.helper.DatabaseHelper.DATABASE_VERSION;
import static com.example.ef.databasecurseradapter.helper.DatabaseHelper.NOTES_TABLE_COLUMN_ID;
import static com.example.ef.databasecurseradapter.helper.DatabaseHelper.NOTES_TABLE_COLUMN_NOTES;
import static com.example.ef.databasecurseradapter.helper.DatabaseHelper.NOTES_TABLE_COLUMN_TIME;
import static com.example.ef.databasecurseradapter.helper.DatabaseHelper.TABLE_NAME;
import static com.example.ef.databasecurseradapter.helper.DatabaseHelper.TAG;

/**
 * Created by EF on 13-Nov-17.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //crate table
        String buildSQL = "CREATE TABLE " + TABLE_NAME + " ( " + NOTES_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                NOTES_TABLE_COLUMN_TIME + " TEXT, " + NOTES_TABLE_COLUMN_NOTES + " TEXT )";

        Log.d(TAG, "onCreate SQL: " + buildSQL);
        sqLiteDatabase.execSQL(buildSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String buildSQL = "DROP TABLE IF EXISTS" + TABLE_NAME;

        Log.d(TAG, "onUpgrade SQL: " + buildSQL);
        sqLiteDatabase.execSQL(buildSQL);  //drop previous table
        onCreate(sqLiteDatabase);     //create the table from the beginning
    }
}
