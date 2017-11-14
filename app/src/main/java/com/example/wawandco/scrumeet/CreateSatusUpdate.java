package com.example.wawandco.scrumeet;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateSatusUpdate extends AppCompatActivity {

    private EditText did, willdo, blockers;
    private Resources resources;
    private FirebaseAuth mAuth;
    String userEmail;
    String userID;
    String username;
    String teamID;

    private Bundle b;
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_satus_update);

        mAuth = FirebaseAuth.getInstance();
        resources = this.getResources();

        did = (EditText)findViewById(R.id.did_value_status);
        willdo = (EditText)findViewById(R.id.willdo_value_status);
        blockers = (EditText)findViewById(R.id.blockers_value_status);

        i = getIntent();
        b = i.getBundleExtra("data");
        teamID = b.get("teamId").toString();

    }

    @Override
    public void onStart() {

        final String[] result = new String[1];


        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent i = new Intent(CreateSatusUpdate.this, Login.class);
            startActivity(i);
        } else {
            userEmail = currentUser.getEmail();
        }



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = database.getReference("Users");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        User usr= snapshot.getValue(User.class);
                        if (usr.getEmail().equals(userEmail)) {
                            userID = usr.getId();
                            username = usr.getName();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("getusers", "Failed to read value.", error.toException());
            }

        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String didVal = did.getText().toString();
        String willdoVal = willdo.getText().toString();

        if (TextUtils.isEmpty(didVal)) {
            did.setError(resources.getString(R.string.required));
            valid = false;
        } else {
            did.setError(null);
        }

        if (TextUtils.isEmpty(willdoVal)) {
            willdo.setError(resources.getString(R.string.required));
            valid = false;
        } else {
            willdo.setError(null);
        }

        return valid;
    }

    public void saveTeam(View v){
        if(validateForm()){

            String id = Data.getStatusId();
            String Username = username;
            String didVal = did.getText().toString();
            String willdoVal = willdo.getText().toString();
            String blockersVal = blockers.getText().toString();

            if (TextUtils.isEmpty(Username)){
                Username = userEmail;
            }

            if (TextUtils.isEmpty(blockersVal)){
                blockersVal = resources.getString(R.string.no_blockers);
            }

            StatusUpdate s = new StatusUpdate(id, userID, Username, teamID, didVal, willdoVal, blockersVal);
            s.saveNewStatus();
            Toast.makeText(CreateSatusUpdate.this, R.string.status_added, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(CreateSatusUpdate.this, TeamHome.class);
            Bundle b = new Bundle();
            b.putString("teamId", teamID);
            i.putExtra("data",b);
            startActivity(i);

        }
    }



}

