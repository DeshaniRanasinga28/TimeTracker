package com.example.ef.databasecurseradapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ef.databasecurseradapter.helper.DatabaseHelper;
import com.example.ef.databasecurseradapter.model.Note;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends ButtonActivity {
    private EditText timeView;
    private EditText  notesView;
    private String id;
    private String time;
    private String notes;
    private DatabaseHelper databaseHelper;
    DatabaseReference darabaseNotes;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        databaseHelper = new DatabaseHelper(this);

        setupView();

    }

    @SuppressLint("WrongViewCast")
    public void setupView(){
        timeView = (EditText)findViewById(R.id.time_view);
        notesView = (EditText)findViewById(R.id.notes_view);

        Intent details = getIntent();
        id = details.getStringExtra("_id");
        Toast.makeText(UpdateActivity.this, id , Toast.LENGTH_SHORT).show();
        time = details.getStringExtra("add_time");
        Toast.makeText(UpdateActivity.this, time, Toast.LENGTH_SHORT).show();
        notes = details.getStringExtra("add_notes");

        timeView.setText(time);
        notesView.setText(notes);
    }

    @Override
    public void onClickUpdate(View view) {
        super.onClickUpdate(view);

        databaseHelper.updateData(
                id,
                timeView.getText().toString(),
                notesView.getText().toString());
        Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onClickCancel(View view) {
        super.onClickCancel(view);
        Toast.makeText(UpdateActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
        finish();
    }
}
