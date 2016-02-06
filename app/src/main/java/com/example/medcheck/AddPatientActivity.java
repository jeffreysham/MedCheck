package com.example.medcheck;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddPatientActivity extends ActionBarActivity {

    List<String> patientList;
    List<String> patientEmailList;
    ListView patientListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        patientList = new ArrayList<>();
        patientEmailList = new ArrayList<>();

        patientListView = (ListView) findViewById(R.id.addPatientList);

        final Context context = this;

        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        final String email = preferences.getString("email", "Email Here");

        Firebase.setAndroidContext(this);
        final Firebase theUserRef = new Firebase("https://medcheck.firebaseio.com/users");

        theUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot userSnapShot: dataSnapshot.getChildren()) {
                        String docName = (String)userSnapShot.child("Doctor Email").getValue();
                        boolean isDoctor = (boolean) userSnapShot.child("isDoctor").getValue();
                        if (!isDoctor && docName == null) {
                            patientList.add((String) userSnapShot.child("Name").getValue());
                            patientEmailList.add((String) userSnapShot.getValue());
                        }
                    }
                    ListAdapter adapter = new ArrayAdapter<>(context, R.layout.patient_item, patientList);
                    patientListView.setAdapter(adapter);
                    patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Firebase alanRef = theUserRef.child(patientEmailList.get(position));
                            Map<String, Object> nickname = new HashMap<String, Object>();
                            nickname.put("Doctor Email", email);
                            alanRef.updateChildren(nickname);
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
