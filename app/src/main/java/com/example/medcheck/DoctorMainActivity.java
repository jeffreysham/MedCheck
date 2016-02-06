package com.example.medcheck;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        getPatients(email);

    }

    private void getPatients(final String email) {
        Firebase theUserRef = new Firebase("https://medcheck.firebaseio.com/users");

        theUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot userSnapShot: dataSnapshot.getChildren()) {
                        if (userSnapShot.child("Doctor Email").getValue().equals(email)){
                            String patientName = (String)userSnapShot.child("Name").getValue();
                            patientsList.add(patientName);
                        }
                    }
                    ListAdapter adapter = new ArrayAdapter<String>(context, R.layout.patient_item, patientsList);
                    patientListView.setAdapter(adapter);
                    patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            patientsList.get(position);
                            //Go to view patients activity
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doctor_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
