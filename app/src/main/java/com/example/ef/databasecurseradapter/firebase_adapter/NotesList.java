package com.example.ef.databasecurseradapter.firebase_adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ef.databasecurseradapter.R;
import com.example.ef.databasecurseradapter.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EF on 28-Nov-17.
 */

public class NotesList extends ArrayAdapter<Note> {
    private Activity context;
    LayoutInflater inflate;
    List<Note> notes;
    TextView viewTime;
    TextView viewNotes;

    public NotesList(Activity context, List<Note> notes){
        super(context, R.layout.row_item, notes);
        this.context = context;
        this.notes = notes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.row_item, null, true);

        viewTime = (TextView)listViewItem.findViewById(R.id.time_view);
        viewNotes = (TextView)listViewItem.findViewById(R.id.notes_view);

        Note note = notes.get(position);
        viewTime.setText(note.getTime());
        viewNotes.setText(note.getNotes());


        return listViewItem;
    }
}























