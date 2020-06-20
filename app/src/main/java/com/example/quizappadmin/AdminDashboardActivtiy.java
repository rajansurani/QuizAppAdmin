package com.example.quizappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.quizappadmin.DataAdapter.InstituteListAdapter;
import com.example.quizappadmin.Model.Institute;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminDashboardActivtiy extends AppCompatActivity {

    private static final String TAG = "AdminDashboard";
    RecyclerView mRecyclerView;
    ArrayList<Institute> mInstitutes;
    InstituteListAdapter mAdapter;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    FloatingActionButton fabNewInstitute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_dashboard_activtiy);

        mDatabase = FirebaseDatabase.getInstance ();

        getSupportActionBar ().setTitle ("All Institutes");
        mInstitutes = new ArrayList<> ();

        mRecyclerView = findViewById (R.id.institute_list_recycler);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (this));
        mAdapter = new InstituteListAdapter (mInstitutes);
        mRecyclerView.setAdapter (mAdapter);

        mReference = mDatabase.getReference ("Institute");

        mReference.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists ())
                {
                    mInstitutes.clear ();
                    for (DataSnapshot data: dataSnapshot.getChildren ())
                    {
                        Log.d (TAG, "onDataChange: "+data);
                        Institute institute = data.getValue (Institute.class);

                        mInstitutes.add (institute);
                        mAdapter.notifyDataSetChanged ();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fabNewInstitute = findViewById (R.id.fab_new_institute);
        fabNewInstitute.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminDashboardActivtiy.this, AddInstituteActivity.class);
                startActivity (intent);
            }
        });

    }
}
