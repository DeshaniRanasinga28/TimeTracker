package com.example.ef.databasecurseradapter.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ef.databasecurseradapter.R;

/**
 * Created by EF on 13-Nov-17.
 */

public class CustomeCurserAdapter extends CursorAdapter {


    public CustomeCurserAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //tell the adapter how much item will lock
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //take the data from the curser and put it in views
        TextView timeView = (TextView)view.findViewById(R.id.time_view);
        timeView.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));

        TextView notesView = (TextView)view.findViewById(R.id.notes_view);
        notesView.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
    }
}
