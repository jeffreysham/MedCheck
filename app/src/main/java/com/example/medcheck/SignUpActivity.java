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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;


public class SignUpActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://medcheck.firebaseio.com");
        Button signupButton = (Button) findViewById(R.id.signupButton);
        final EditText nameText = (EditText) findViewById(R.id.signupNameText);
        final EditText emailText = (EditText) findViewById(R.id.signupEmailText);
        final EditText passText = (EditText) findViewById(R.id.signupPasswordText);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.doctorCheckbox);
        final Context context = this;

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailString = emailText.getText().toString().trim();
                final String passwordString = passText.getText().toString().trim();

                ref.createUser(emailString, passwordString, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        //System.out.println("Successfully created user account with uid: " + result.get("uid"));
                        Toast.makeText(context, "Signed Up Successful", Toast.LENGTH_SHORT).show();
                        Firebase newUserRef = ref.child("users").child(emailString.substring(0,emailString.indexOf(".")));

                        newUserRef.child("Name").setValue(nameText.getText().toString().trim());
                        newUserRef.child("Doctor Name").setValue("");
                        if (checkBox.isChecked()) {
                            newUserRef.child("isDoctor").setValue(true);
                        } else {
                            newUserRef.child("isDoctor").setValue(false);
                        }

                        ref.authWithPassword(emailString, passwordString, new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                                Toast.makeText(context, "Logging In...", Toast.LENGTH_SHORT).show();
                                //Go to decision screen
                                SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
                                preferences.edit().putString("name", nameText.getText().toString().trim()).apply();
                                preferences.edit().putString("email", emailString.substring(0,emailString.indexOf("."))).apply();
                                if (checkBox.isChecked()) {
                                    //Go to doctor app
                                    Intent intent = new Intent(context, DoctorMainActivity.class);
                                    startActivity(intent);
                                } else {
                                    //Go to patient app
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                // there was an error
                                Toast.makeText(context, "Error Logging In", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // there was an error
                        Toast.makeText(context, "Error Signing Up. Try again", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

}
