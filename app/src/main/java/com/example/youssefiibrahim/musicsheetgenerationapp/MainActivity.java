package com.example.youssefiibrahim.musicsheetgenerationapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.youssefiibrahim.musicsheetgenerationapp.Common.Common;
import com.example.youssefiibrahim.musicsheetgenerationapp.Common.MyFocusChangeListener;
import com.example.youssefiibrahim.musicsheetgenerationapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MaterialEditText edtNewUsername, edtNewPassword, edtNewEmail; //for Sign Up
    MaterialEditText edtUser, edtPassword; //for Sign In

    Button btnSignUp, btnSignIn;

    FirebaseDatabase database;
    DatabaseReference users;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    FirebaseUser fireUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        //Edit texts
        edtUser = (MaterialEditText) findViewById(R.id.edtUser);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);

        //Listeners
        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
        edtUser.setOnFocusChangeListener(ofcListener);
        edtPassword.setOnFocusChangeListener(ofcListener);


        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtUser.getText().toString(),edtPassword.getText().toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    private void signIn(final String email, final String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm(2)) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            closeKeyboard();
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            users.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot data:dataSnapshot.getChildren())
                                    {
                                        Intent homeActivity = new Intent(MainActivity.this, Home.class);
                                        Log.d("Current User 1 : ", (data.toString()));
//                                        Log.d("Current User 1 : ", (data.getChildren().getClass().getName()));
//                                        Log.d("Already Current User: ", Common.currentUser.toString());
//                                        Log.d("Current User 2 : ", (data.getValue(User.class).getSavedLinks()).toString());
                                        Common.currentUser = data.getValue(User.class);
                                        startActivity(homeActivity);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            closeKeyboard();
                            Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showSignUpDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Please fill all the information");

        LayoutInflater inflater = this.getLayoutInflater();
        final View sign_up_layout = inflater.inflate(R.layout.sign_up_layout, null);

        edtNewUsername = (MaterialEditText) sign_up_layout.findViewById(R.id.edtNewUsername);
        edtNewPassword = (MaterialEditText) sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail = (MaterialEditText) sign_up_layout.findViewById(R.id.edtNewEmail);

        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
        edtNewUsername.setOnFocusChangeListener(ofcListener);
        edtNewPassword.setOnFocusChangeListener(ofcListener);
        edtNewEmail.setOnFocusChangeListener(ofcListener);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Boolean[] wantToCloseDialog = {false};
                Log.d(TAG, "createAccount:" + edtNewEmail.getText().toString());
                if(!validateForm(3)) {
                    wantToCloseDialog[0] = false;
                }
                else {
                    List<String> dummyLinks = new ArrayList<String>();
                    final User user = new User(edtNewUsername.getText().toString(), edtNewEmail.getText().toString(), dummyLinks);

                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(user.getUsername()).exists()) {
                                //closeKeyboard();
                                edtNewUsername.setError("Username already exists.");
                                wantToCloseDialog[0] = false;
                                //Toast.makeText(MainActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                            } else {
                                users.child(user.getUsername()).setValue(user);
                                mAuth.createUserWithEmailAndPassword(edtNewEmail.getText().toString(), edtNewPassword.getText().toString())
                                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Log.d(TAG, "createUserWithEmail:success");
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    closeKeyboard();
                                                    dialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    if(task.getException().getMessage().toLowerCase().indexOf("Email".toLowerCase()) != -1) {
                                                        edtNewEmail.setError(task.getException().getMessage());
                                                    }
                                                    if(task.getException().getMessage().toLowerCase().indexOf("Password".toLowerCase()) != -1) {
                                                        edtNewPassword.setError(task.getException().getMessage());
                                                    }
                                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                                    edtNewEmail.setError("Email already registered!");
                                                    wantToCloseDialog[0] = false;
                                                    closeKeyboard();

                                                }
                                            }
                                        });
                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                if(wantToCloseDialog[0])
                    dialog.dismiss();
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean validateForm(int param) {
        boolean valid = true;
        if(param == 3) {
            String username = edtNewUsername.getText().toString();
            if (TextUtils.isEmpty(username)) {
                edtNewUsername.setError("Required.");
                valid = false;
            } else {
                edtNewUsername.setError(null);
            }

            String email = edtNewEmail.getText().toString();
            if (TextUtils.isEmpty(email)) {
                edtNewEmail.setError("Required.");
                valid = false;
            } else {
                edtNewEmail.setError(null);
            }

            String password = edtNewPassword.getText().toString();
            if (TextUtils.isEmpty(password)) {
                edtNewPassword.setError("Required.");
                valid = false;
            } else {
                edtNewPassword.setError(null);
            }
        } else {
            String email = edtUser.getText().toString();
            if (TextUtils.isEmpty(email)) {
                edtUser.setError("Required.");
                valid = false;
            } else {
                edtUser.setError(null);
            }

            String password = edtPassword.getText().toString();
            if (TextUtils.isEmpty(password)) {
                edtPassword.setError("Required.");
                valid = false;
            } else {
                edtPassword.setError(null);
            }
        }



        return valid;
    }
}
