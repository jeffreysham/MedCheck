package com.example.medcheck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://medcheck.firebaseio.com");
        Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText emailText = (EditText) findViewById(R.id.loginEmailText);
        final EditText passText = (EditText) findViewById(R.id.loginPasswordText);
        Button signupButton = (Button) findViewById(R.id.loginSignupButton);
        final Context context = this;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailString = emailText.getText().toString().trim();
                String passwordString = passText.getText().toString().trim();
                ref.authWithPassword(emailString, passwordString, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                        Toast.makeText(context, "Logging In...", Toast.LENGTH_SHORT).show();
                        //Go to decision screen
                        Firebase theUserRef = new Firebase("https://medcheck.firebaseio.com/users");
                        Toast.makeText(context, "Email: " + emailString.substring(0,emailString.indexOf(".")), Toast.LENGTH_SHORT).show();
                        Query query = theUserRef;
                                //theUserRef.equalTo(emailString.substring(0,emailString.indexOf(".")));

                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                boolean isDoctor = (boolean)dataSnapshot.child("isDoctor").getValue();
                                SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
                                preferences.edit().putString("name", (String)dataSnapshot.child("Name").getValue()).apply();
                                preferences.edit().putString("email", emailString.substring(0,emailString.indexOf("."))).apply();
                                Toast.makeText(context, "Logging In Complete", Toast.LENGTH_SHORT).show();
                                if (isDoctor) {
                                    //Go to doctor app
                                    Intent intent = new Intent(context, DoctorMainActivity.class);
                                    startActivity(intent);
                                } else {
                                    //Go to user app
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                boolean isDoctor = (boolean)dataSnapshot.child("isDoctor").getValue();
                                SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
                                preferences.edit().putString("name", (String)dataSnapshot.child("Name").getValue()).apply();
                                preferences.edit().putString("email", emailString.substring(0,emailString.indexOf("."))).apply();
                                Toast.makeText(context, "Logging In Complete", Toast.LENGTH_SHORT).show();
                                if (isDoctor) {
                                    //Go to doctor app
                                    Intent intent = new Intent(context, DoctorMainActivity.class);
                                    startActivity(intent);
                                } else {
                                    //Go to user app
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                }                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                boolean isDoctor = (boolean)dataSnapshot.child("isDoctor").getValue();
                                SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
                                preferences.edit().putString("name", (String)dataSnapshot.child("Name").getValue()).apply();
                                preferences.edit().putString("email", emailString.substring(0,emailString.indexOf("."))).apply();
                                Toast.makeText(context, "Logging In Complete", Toast.LENGTH_SHORT).show();
                                if (isDoctor) {
                                    //Go to doctor app
                                    Intent intent = new Intent(context, DoctorMainActivity.class);
                                    startActivity(intent);
                                } else {
                                    //Go to user app
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // there was an error
                        Toast.makeText(context, "Error Logging In", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
