package com.example.ef.databasecurseradapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ef.databasecurseradapter.fragment.ItemFragment;
import com.example.ef.databasecurseradapter.helper.DatabaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.AccessControlContext;

import static java.security.AccessController.getContext;

public class DetailsActivity extends ButtonActivity {
    private String id;
    private String time;
    private String notes;
    private EditText timeView;
    private EditText notesView;
    private DatabaseHelper databaseHelper;
    private ItemFragment itemFragment;
    DatabaseReference darabaseNotes;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);

        //get Reference of the notes node
        darabaseNotes = FirebaseDatabase.getInstance().getReference("notes");

        itemFragment = new ItemFragment();
        setContentView(R.layout.activity_details);

        timeView = (EditText) findViewById(R.id.time_view);
        notesView = (EditText) findViewById(R.id.notes_view);

        setView();
    }

    private void setView(){
        //Check Internet Connection
        if(checkInternetConnection()== true){
            firebaseSetup();
        }else{
            SqliteSetup();
        }
    }

    //Firebase View
    private void firebaseSetup(){
        Intent details = this.getIntent();
        id = details.getStringExtra("id");
        time = details.getStringExtra("time");
        notes = details.getStringExtra("notes");

        timeView.setText(time);
        notesView.setText(notes);
    }

    //Sql View
    private void SqliteSetup(){
        Intent details = this.getIntent();
        id = details.getStringExtra("_id");
        //Toast.makeText(DetailsActivity.this, id , Toast.LENGTH_SHORT).show();
        time = details.getStringExtra("add_time");
        notes = details.getStringExtra("add_notes");

        timeView.setText(time);
        notesView.setText(notes);
    }

    @Override
    public void onClickCancel(View view) {
        //super.onClickCancel(view);
        //Toast.makeText(DetailsActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClickDelete(View view) {
        super.onClickDelete(view);
        if(checkInternetConnection() == true){
            new AlertDialog.Builder(DetailsActivity.this)
                    .setTitle("Delete Item")
                    .setMessage("Delete selected Item")
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    darabaseNotes.child(id).removeValue();
                                    Toast.makeText(DetailsActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                                    finish();
                                    dialogInterface.cancel();
                                }
                            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();

        }else{
            new AlertDialog.Builder(DetailsActivity.this)
                    .setTitle("Delete Item")
                    .setMessage("Delete selected Item")
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    databaseHelper.deleteItem(id);
                                    Toast.makeText(DetailsActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                                    finish();
                                    dialogInterface.cancel();
                                }
                            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        }
    }



    @Override
    public void onClickEdit(View view) {

        if (checkInternetConnection() == true) {
            String noteTime = timeView.getText().toString();
            String noteNotes = notesView.getText().toString();
            notesView.getText();
            updateFirebase(id, noteTime, noteNotes);
        } else {
            if (time.length() != 0 && notes.length() != 0) {

                databaseHelper.updateData(
                        id,
                        timeView.getText().toString(),
                        notesView.getText().toString());
            }
        }

        Toast.makeText(DetailsActivity.this, "Updated", Toast.LENGTH_LONG).show();
        //this.setResult(RESULT_OK);
        finish();
    }

    private void updateFirebase(String id,String time, String notes){
        if(!TextUtils.isEmpty(time)){
            darabaseNotes.child(id).child("time").setValue(time);
        }
        if(!TextUtils.isEmpty(notes)){
            darabaseNotes.child(id).child("notes").setValue(notes);
        }
    }


    //Check Internet connection
    public boolean checkInternetConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(DetailsActivity.this, "Connected", Toast.LENGTH_LONG).show();
            return true;
        }else {
            Toast.makeText(DetailsActivity.this, "Network Connection is not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.about:
                return true;
            case R.id.setting:
                return true;
            case R.id.refresh:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
