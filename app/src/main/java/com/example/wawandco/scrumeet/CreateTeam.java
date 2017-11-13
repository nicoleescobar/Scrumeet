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

import java.util.ArrayList;

public class CreateTeam extends AppCompatActivity {

    private EditText teamNameTxt;
    private TextView team_key_value;
    public String teamKey;
    private Resources resources;
    private FirebaseAuth mAuth;
    String userEmail;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        mAuth = FirebaseAuth.getInstance();
        resources = this.getResources();

        teamNameTxt = (EditText)findViewById(R.id.teamNameTxt);
        team_key_value = (TextView) findViewById(R.id.team_key_value);
        teamKey = Data.getTeamId();

        team_key_value.setText(teamKey);
    }

    @Override
    public void onStart() {

        final String[] result = new String[1];


        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent i = new Intent(CreateTeam.this,Login.class);
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

        String teamName = teamNameTxt.getText().toString();
        if (TextUtils.isEmpty(teamName)) {
            teamNameTxt.setError(resources.getString(R.string.required));
            valid = false;
        } else {
            teamNameTxt.setError(null);
        }

        return valid;
    }

    public void saveTeam(View v){
        if(validateForm()){

            String id = teamKey;
            String teamName = teamNameTxt.getText().toString();
            boolean isActive = true;
            int membersCount = 0;

            Team t = new Team(id, teamName, userID, isActive, membersCount );
            t.saveNewTeam();
            Toast.makeText(CreateTeam.this, R.string.team_saved, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(CreateTeam.this,TeamList.class);
            startActivity(i);

        }
    }
}
