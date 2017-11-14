package com.example.wawandco.scrumeet;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamHome extends AppCompatActivity implements StatusAdapter.OnStatusClickListener {
    private RecyclerView updatesList;
    private ArrayList<StatusUpdate> userUpdates;
    private Resources res;
    private StatusAdapter adapter;
    private LinearLayoutManager llm;
    private DatabaseReference databaseReference;
    private final String BD="UserUpdates";
    private FirebaseAuth mAuth;
    String userEmail;
    String userID;
    String teamId;

    private Bundle b;
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_home);

        updatesList = (RecyclerView)findViewById(R.id.updatesList);
        res = this.getResources();
        mAuth = FirebaseAuth.getInstance();

        i = getIntent();
        b = i.getBundleExtra("data");

        if (b != null){
            teamId = b.get("teamId").toString();
        }


        userUpdates = new ArrayList<>();
        adapter = new StatusAdapter(this.getApplicationContext(), userUpdates, this);
        llm = new LinearLayoutManager(this);
        updatesList.setLayoutManager(llm);
        updatesList.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void onStatusClick(StatusUpdate userUpdates) {
        Intent i = new Intent(TeamHome.this, UserProfile.class);
        Bundle b = new Bundle();
        b.putString("userId", userUpdates.getUserId());
        b.putString("teamId", teamId);
        i.putExtra("data",b);
        startActivity(i);
    }


    @Override
    public void onStart() {

        final String[] result = new String[1];


        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent i = new Intent(TeamHome.this,Login.class);
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
                        if (usr.getEmail().equalsIgnoreCase(userEmail)) {
                            userID = usr.getId();
                            loadUserUpdates();
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

    public void loadUserUpdates() {

        databaseReference.child(BD).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                userUpdates.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        StatusUpdate s = snapshot.getValue(StatusUpdate.class);
                        if (s.getTeamId().equals(teamId)) {
                            userUpdates.add(s);

                        }
                    }
                }
                adapter.notifyDataSetChanged();
                Data.setStatus(userUpdates);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void goToNewStatus(View v){
        Intent i = new Intent(TeamHome.this, CreateSatusUpdate.class);
        Bundle b = new Bundle();
        b.putString("id", userID);
        b.putString("teamId", teamId);
        i.putExtra("data",b);
        startActivity(i);
    }

    public void goToUserProfile(View v){
        Intent i = new Intent(TeamHome.this, UserProfile.class);
        Bundle b = new Bundle();
        b.putString("userId", userID);
        b.putString("teamId", teamId);
        i.putExtra("data",b);
        startActivity(i);
    }
}
