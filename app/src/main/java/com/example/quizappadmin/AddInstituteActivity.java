package com.example.quizappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizappadmin.Model.Institute;
import com.example.quizappadmin.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class AddInstituteActivity extends AppCompatActivity {

    EditText etName, etCode, etEmail, etPassword;
    Button btnAdd;

    FirebaseAuth mAuth,mAuth2;
    private ProgressDialog mDialog;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_institute);

        getSupportActionBar ().setTitle ("New Institute");
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

        mAuth = FirebaseAuth.getInstance ();

        etName = findViewById (R.id.etName);
        etCode = findViewById (R.id.etCode);
        etEmail = findViewById (R.id.etInstituteEmail);
        etPassword = findViewById (R.id.etInstitutePassword);

        btnAdd = findViewById (R.id.btnAdd);

        mDatabase = FirebaseDatabase.getInstance ();

        mDialog = new ProgressDialog (this);
        mDialog.setMessage ("Adding Institute...");
        btnAdd.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mDialog.show ();
                final String name = etName.getText ().toString ();
                final String code = etCode.getText ().toString ();
                final String email = etEmail.getText ().toString ();
                final String password = etPassword.getText ().toString ();

                final Institute institute = new Institute (name, email, code);
                final User u= new User(name,email,code,"","","Institute");
                FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                        .setDatabaseUrl("https://quiz-app-8e162.firebaseio.com/")
                        .setApiKey("AIzaSyBjNo7_EQhbPLRcF0qT23Fo3maiVyGQRac")
                        .setApplicationId("quiz-app-8e162").build();

                try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "QuizAppAdmin");
                    mAuth2 = FirebaseAuth.getInstance(myApp);
                } catch (IllegalStateException e){
                    mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("QuizAppAdmin"));
                }

                mAuth2.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener (new OnSuccessListener<AuthResult> () {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("SignUp", "createUserWithEmail:success");
                                    final FirebaseUser user = mAuth2.getCurrentUser();

                                    if(user!=null)
                                    {
                                        UserProfileChangeRequest profile = new  UserProfileChangeRequest.Builder()
                                                .setDisplayName (name)
                                                .build ();

                                        user.updateProfile (profile)
                                                .addOnCompleteListener (new OnCompleteListener<Void> () {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful ()){
                                                            Log.d ("SignUp","Display name updated");
                                                            mReference = mDatabase.getReference ("Institute");
                                                            mReference.child (code).setValue (institute);
                                                            mReference = mDatabase.getReference ("Users");
                                                            mReference.child (user.getUid ()).setValue (u);
                                                            mDialog.dismiss ();
                                                            mAuth2.signOut ();
                                                            finish ();

                                                        }
                                                        else
                                                            Log.d ("SignUp","Display name not updated");
                                                    }
                                                });
                                    }

                            }
                        });
            }
        });

    }
}
