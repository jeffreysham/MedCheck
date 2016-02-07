package com.example.medcheck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;


public class DoctorMainActivity extends ActionBarActivity {

    ArrayList<String> patientsList;
    ArrayList<String> patientEmailList;
    ListView patientListView;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        Firebase.setAndroidContext(this);
        TextView userGreetingView = (TextView) findViewById(R.id.doctorGreetingText);
        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "Username Here");
        String email = preferences.getString("email", "Email Here");
        userGreetingView.setText("Hello, " + name);

        patientListView = (ListView) findViewById(R.id.patientList);
        patientsList = new ArrayList<>();
        patientEmailList = new ArrayList<>();
        getPatients(email);

        ImageButton button = (ImageButton) findViewById(R.id.addPatientButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddPatientActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getPatients(final String email) {
        Firebase theUserRef = new Firebase("https://medcheck.firebaseio.com/users");

        theUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    Log.i("snapshot", dataSnapshot.toString());
                    if (dataSnapshot.child("Doctor Email") != null && dataSnapshot.child("Doctor Email").getValue().equals(email)) {
                        String patientName = (String)dataSnapshot.child("Name").getValue();
                        patientsList.add(patientName);
                        patientEmailList.add((String) dataSnapshot.getKey());
                    }

                    ListAdapter adapter = new ArrayAdapter<String>(context, R.layout.patient_item, patientsList);
                    patientListView.setAdapter(adapter);
                    patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            //Go to view patients activity
                            Intent intent = new Intent(context, ViewPatientActivity.class);
                            intent.putExtra("patient email", patientEmailList.get(position));
                            intent.putExtra("patient name", patientsList.get(position));
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
