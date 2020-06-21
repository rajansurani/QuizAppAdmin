package com.example.quizappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizappadmin.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button btnLogin;
    TextView tvSignup;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public static String MY_PREFS_NAME= "CurrentUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        etEmail= findViewById(R.id.etEmail);
        etPassword= findViewById(R.id.etPassword);
        btnLogin= findViewById(R.id.btnLogin);
        tvSignup= findViewById(R.id.tvSignup);

        mDatabase = FirebaseDatabase.getInstance ();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User user = gson.fromJson(json, User.class);

        if(user!=null)
        {
            if(user.getRole ().equals ("Admin"))
            {
                Intent intent = new Intent (MainActivity.this, AdminDashboardActivtiy.class);
                finish ();
                startActivity (intent);
            }
        }
    }

    public void login(View v)
    {
        final ProgressDialog mDialog = new ProgressDialog (this);
        mDialog.setMessage ("Logging In...");
        mDialog.show ();
        String email=etEmail.getText().toString().trim();
        String password=etPassword.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication sucess.",
                                    Toast.LENGTH_SHORT).show();

                            mReference = mDatabase.getReference ("Users/"+mAuth.getCurrentUser ().getUid ());
                            mReference.addListenerForSingleValueEvent (new ValueEventListener () {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue (User.class);
                                    mDialog.dismiss ();
                                    Intent intent = null;
                                    if(user.getRole ().equals ("Admin"))
                                        intent = new Intent (MainActivity.this, AdminDashboardActivtiy.class);

                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(user);
                                    editor.putString("user", json);
                                    editor.commit();
                                    finish ();
                                    startActivity (intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            mDialog.dismiss ();
                        }

                        // ...
                    }
                });

    }


}
