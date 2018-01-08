package com.example.ef.databasecurseradapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;

public abstract class ButtonActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

    }

    //cancel button
    public void onClickCancel(View view){

    }

    //Delete button
    public void onClickDelete(View view){

    }

    //Edit Button
    public void onClickEdit(View view){

    }

    //Update Button
    public void onClickUpdate(View view){

    }

    //Save Button
    public void onClickSave(View view){

    }

}
