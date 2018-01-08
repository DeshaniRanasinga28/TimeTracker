package com.example.ef.databasecurseradapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.support.design.widget.CollapsingToolbarLayout;

import com.example.ef.databasecurseradapter.adapter.CustomeCurserAdapter;
import com.example.ef.databasecurseradapter.fragment.ItemFragment;
import com.example.ef.databasecurseradapter.helper.DatabaseHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.app.Activity.RESULT_OK;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnFragmentInteractionListener {
    private CustomeCurserAdapter customeAdapter;
    private DatabaseHelper databaseHelper;
    private static final int ENTER_DATA_REQUEST_CODE = 1;
    private ListView listView;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ItemFragment fragment = ItemFragment.newInstance("", "", "");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        // fragmen container id in first parameter is the  container(Main layout id) of Activity
        // this will manage backstack
        transaction.addToBackStack(null);
        transaction.commit();

        FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(getApplicationContext(), FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
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
