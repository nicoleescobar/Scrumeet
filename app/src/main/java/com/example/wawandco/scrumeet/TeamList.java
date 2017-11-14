package com.example.wawandco.scrumeet;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class TeamList extends AppCompatActivity implements TeamAdapter.OnTeamClickListener {
    private RecyclerView listado;
    private ArrayList<Team> teams;
    private Resources res;
    private TeamAdapter adapter;
    private LinearLayoutManager llm;
    private DatabaseReference databaseReference;
    private final String BD="Teams";
    private FirebaseAuth mAuth;
    String userEmail;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        listado = (RecyclerView)findViewById(R.id.teamList);
        res = this.getResources();
        mAuth = FirebaseAuth.getInstance();

        teams = new ArrayList<>();
        adapter = new TeamAdapter(this.getApplicationContext(),teams,this);
        llm = new LinearLayoutManager(this);
        listado.setLayoutManager(llm);
        listado.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onTeamClick(Team t) {
        Intent i = new Intent(TeamList.this,TeamHome.class);
        Bundle b = new Bundle();
        b.putString("teamId",t.getId());
        i.putExtra("data",b);
        startActivity(i);

    }

    public void addNewTeam(View v) {
        Intent i = new Intent(TeamList.this,CreateTeam.class);
        startActivity(i);
    }

    @Override
    public void onStart() {

        final String[] result = new String[1];


        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent i = new Intent(TeamList.this,Login.class);
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
                            loadTeams();
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

    public void loadTeams (){
        databaseReference.child(BD).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                teams.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Team t= snapshot.getValue(Team.class);
                        if (t.getManager().equals(userID)) {
                            teams.add(t);

                        }
                    }
                }
                adapter.notifyDataSetChanged();
                Data.setTeam(teams);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
