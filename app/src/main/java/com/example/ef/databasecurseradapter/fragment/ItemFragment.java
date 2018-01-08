package com.example.ef.databasecurseradapter.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ef.databasecurseradapter.DetailsActivity;
import com.example.ef.databasecurseradapter.MainActivity;
import com.example.ef.databasecurseradapter.R;
import com.example.ef.databasecurseradapter.SaveDataActivity;
import com.example.ef.databasecurseradapter.adapter.CustomeCurserAdapter;
import com.example.ef.databasecurseradapter.firebase_adapter.NotesList;
import com.example.ef.databasecurseradapter.helper.DatabaseHelper;
import com.example.ef.databasecurseradapter.helper.DatabaseOpenHelper;
import com.example.ef.databasecurseradapter.model.Note;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static android.R.*;
import static android.R.layout.simple_dropdown_item_1line;
import static android.app.Activity.RESULT_OK;
import static com.example.ef.databasecurseradapter.SaveDataActivity.NOTES_ID;
import static junit.runner.Version.id;

import com.example.ef.databasecurseradapter.model.Note.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private OnFragmentInteractionListener mListener;

    private CustomeCurserAdapter customeAdapter;
    private NotesList notesListAdapter;
    private DatabaseHelper databaseHelper;
    private static final int ENTER_DATA_REQUEST_CODE = 1;
    private ListView listView;
    private FloatingActionButton addButton;

    //database reference object
    DatabaseReference darabaseNotes;
    String id;
    List<Note> notes;
    FirebaseDatabase firebaseDatabase;



    public ItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemFragment newInstance(String param1, String param2, String param3) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);              
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_item, container, false);
        return rootView;
    }

    //Open DetailsActivity
    public void setupView(){
        if(checkInternetConnection(getContext())== false){
            setUpSqlView();
        }else{
            setUpFirebaseView();
        }

//        listView = (ListView)rootView.findViewById(R.id.list_view);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
//                //Log.d(TAG, "clicked on item: " + position);
//                //Create Cursor object
//                Cursor item = (Cursor)customeAdapter.getItem(position);
//
//                //String time = editTime.getText().toString();
//                //String notes = editNotes.getText().toString();
//
//                //review values
//                String id = item.getString(item.getColumnIndex("_id"));
//                String time = item.getString(item.getColumnIndex("time"));
//                String notes = item.getString(item.getColumnIndex("notes"));
//
////                Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
//                Intent details = new Intent(getActivity(), DetailsActivity.class);
//
//                details.putExtra("_id", id);
//                details.putExtra("add_time", time);
//                details.putExtra("add_notes", notes);
//                startActivity(details);
//            }
//        });


        //Cancel Button
        addButton = (FloatingActionButton )rootView.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), SaveDataActivity.class), ENTER_DATA_REQUEST_CODE);

            }
        });

//        new Handler().post(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        customeAdapter = new CustomeCurserAdapter(getActivity(), databaseHelper.getAllData());
//                        listView.setAdapter(customeAdapter);
//                    }
//                });

        onViewCreated(null, null);
    }

    //setup SQLlite View     
    public void setUpSqlView(){
        listView = (ListView)rootView.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
                //Log.d(TAG, "clicked on item: " + position);
                //Create Cursor object
                Cursor item = (Cursor)customeAdapter.getItem(position);

                String id = item.getString(item.getColumnIndex("_id"));
                String time = item.getString(item.getColumnIndex("time"));
                String notes = item.getString(item.getColumnIndex("notes"));

                Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
                Intent details = new Intent(getActivity(), DetailsActivity.class);

                details.putExtra("_id", id);
                details.putExtra("add_time", time);
                details.putExtra("add_notes", notes);
                startActivity(details);

            }

        });
    }


    public void setUpFirebaseView(){
        listView = (ListView)rootView.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                darabaseNotes = FirebaseDatabase.getInstance().getReference("notes").child("id");

                Note note = notes.get(position);
                String id = note.getId();
                String time = note.getTime();
                String notes = note.getNotes();
//                Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();

                Intent details = new Intent(getActivity(), DetailsActivity.class);
                details.putExtra("id", id);
                details.putExtra("time", time);
                details.putExtra("notes", notes);
//                Toast.makeText(getActivity(), "open", Toast.LENGTH_SHORT).show();
               startActivity(details);

            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(checkInternetConnection(getContext())== false){
            if(requestCode == ENTER_DATA_REQUEST_CODE && resultCode == RESULT_OK){
                databaseHelper.insertData(
                        //insert values foe database
                        data.getExtras().getString("add_time"),
                        data.getExtras().getString("add_notes"));
                customeAdapter.changeCursor(databaseHelper.getAllData());
            }

        }

                                 
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        //Check internet connection
                        if(checkInternetConnection(getContext())== true){
                            onFirebaseListView();
                        }else{
                            onLoadListView();                                                                                 
                        }

                        //get SQLite database values
//                        customeAdapter = new CustomeCurserAdapter(getActivity(), databaseHelper.getAllData());
//                        listView.setAdapter(customeAdapter);
//                        Toast.makeText(getActivity(), "LoadListView", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    @Override
    public void onResume() {
        super.onResume();
        setupView();
    }

    //Check Internet Connection
    public boolean checkInternetConnection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(getActivity(), "Connected", Toast.LENGTH_LONG).show();
            return true;
        }else {
            return false;
        }
    }

    //set SQLite values for ListView
    public void onLoadListView(){
        customeAdapter = new CustomeCurserAdapter(getActivity(), databaseHelper.getAllData());
        listView.setAdapter(customeAdapter);
    }


    //RetView Firebase database values in ListView
    protected void onFirebaseListView(){
        listView = (ListView)rootView.findViewById(R.id.list_view);
        Intent intent = getActivity().getIntent();
        darabaseNotes = FirebaseDatabase.getInstance().getReference("notes");
        notes = new ArrayList<>();
        darabaseNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notes.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Note note = postSnapshot.getValue(Note.class);
                    notes.add(note);
                }
                notesListAdapter = new NotesList(getActivity(), notes);
                listView.setAdapter(notesListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}



















