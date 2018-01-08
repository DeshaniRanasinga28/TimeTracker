package com.example.ef.databasecurseradapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ef.databasecurseradapter.fragment.ItemFragment;
import com.example.ef.databasecurseradapter.model.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SaveDataActivity extends ButtonActivity {
    //use these constants later to pass the id, time and notes to another activity
    public static final String NOTES_ID = "com.example.ef.databasecurseradapter.model.id";
    public static final String NOTES_TIME = "com.example.ef.databasecurseradapter.model.notes";
    public static final String NOTES_NOTE = "com.example.ef.databasecurseradapter.model.time";


    EditText editTime;
    EditText editNotes;
    //database reference object
    DatabaseReference darabaseNotes;
    String id;
    List<Note> notes;
    FirebaseDatabase firebaseDatabase;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        //get Reference of the notes node
        darabaseNotes = FirebaseDatabase.getInstance().getReference("notes");

        editTime = (EditText)findViewById(R.id.time_view);
        editNotes = (EditText)findViewById(R.id.notes_view);

        notes = new ArrayList<>();
    }



    @Override
    public void onClickSave(View view){
        Intent newIntent = getIntent();

        String time = editTime.getText().toString();
        String notes = editNotes.getText().toString();

        ConnectivityManager connMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            if (TextUtils.isEmpty(id)) {
                //Adda vales for Firebase
                id = darabaseNotes.push().getKey();
                onSaveFirebaseNotes(id, time, notes);
                newIntent.putExtra("id", id);
                newIntent.putExtra("time", time);
                newIntent.putExtra("notes", notes);
                Toast.makeText(SaveDataActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                Toast.makeText(SaveDataActivity.this, id, Toast.LENGTH_SHORT).show();
                Toast.makeText(SaveDataActivity.this, time, Toast.LENGTH_SHORT).show();
                Toast.makeText(SaveDataActivity.this, notes, Toast.LENGTH_SHORT).show();
                this.setResult(RESULT_OK, newIntent);
                finish();
            }
        }else {
            if (time.length() != 0 && notes.length() != 0) {
                //Add values for SQLite
//            newIntent.putExtra("add_time", id);
                newIntent.putExtra("add_time", time);
                newIntent.putExtra("add_notes", notes);
                Toast.makeText(SaveDataActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                this.setResult(RESULT_OK, newIntent);
                finish();
            }
        }

    }

    //Insert values in Firebase database
    public void onSaveFirebaseNotes(String id, String time, String notes){
        Note note = new Note(id, time, notes);
        darabaseNotes.child(id).setValue(note);
    }


    @Override
    public void onClickCancel(View view) {
        super.onClickCancel(view);
        //Toast.makeText(SaveDataActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SaveDataActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
