package com.example.wawandco.scrumeet;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class UserProfileEdit extends AppCompatActivity {
    private Resources res;
    private FirebaseAuth mAuth;
    String userID;
    String teamId;
    String projectName;

    private Bundle b;
    private Intent i;

    private  User usr_data;

    private EditText username , birthday, country, cc , tastes, shirt, shoes, pants, hobbies, food, movies, music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);

        res = this.getResources();
        mAuth = FirebaseAuth.getInstance();
        usr_data = new User();

        i = getIntent();
        b = i.getBundleExtra("data");

        if (b != null){
            teamId = b.get("teamId").toString();
            userID = b.get("userId").toString();
        }
        getProject();

        username = (EditText) findViewById(R.id.username_val);
        birthday = (EditText) findViewById(R.id.birthday_val);
        tastes = (EditText) findViewById(R.id.tastes_val);
        shirt = (EditText) findViewById(R.id.shirt_val);
        shoes = (EditText) findViewById(R.id.shoes_val);
        pants = (EditText) findViewById(R.id.pants_val);
        hobbies = (EditText) findViewById(R.id.hobbies_val);
        movies = (EditText) findViewById(R.id.movies_val);
        music = (EditText) findViewById(R.id.music_val);
        country = (EditText) findViewById(R.id.country_val);
        cc = (EditText) findViewById(R.id.cc_val);
        food = (EditText) findViewById(R.id.food_val);

        loadUserProfile();
    }

    public void loadUserProfile (){
        Log.e("console", "user id: "+userID);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = database.getReference("Users");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        User usr= snapshot.getValue(User.class);
                        if (usr.getId().equals(userID)) {
                            setUserProfile(usr);
                            usr_data = usr;
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

    public void setUserProfile(User usr){

        username.setText(usr.getName());
        birthday.setText(usr.getBirthday());
        tastes.setText(usr.getTastes());
        shirt.setText(usr.getShirtSize());
        shoes.setText(usr.getShoesSize());
        pants.setText(usr.getPantsSize());
        hobbies.setText(usr.getHobbies());
        movies.setText(usr.getMovies());
        music.setText(usr.getMusic());
        country.setText(usr.getCountry());
        cc.setText(usr.getCc());
        food.setText(usr.getFood());

    }

    public void saveTeam(View v){
        String id = usr_data.getId();
        String email = usr_data.getEmail();
        Boolean isManager = usr_data.getManager();
        String room = usr_data.getRoom();
        String photo = "genericThumb";
        String username_val = username.getText().toString();
        String birthday_val =  birthday.getText().toString();
        String tastes_val = tastes.getText().toString();
        String shirtSize = shirt.getText().toString();
        String shoesSize = shoes.getText().toString();
        String pantsSize = pants.getText().toString();
        String hobbies_val = hobbies.getText().toString();
        String movies_val = movies.getText().toString();
        String music_val = music.getText().toString();
        String country_val = country.getText().toString();
        String cc_val = cc.getText().toString();
        String food_val = food.getText().toString();

        String goalsDone = "1,2,3";


        User s = new User(id, email, isManager, room, photo, cc_val, username_val, birthday_val, shirtSize, shoesSize, pantsSize, projectName, hobbies_val, movies_val, music_val, food_val, tastes_val, goalsDone, country_val);
        s.update();
        Toast.makeText(UserProfileEdit.this, R.string.user_updated, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(UserProfileEdit.this, UserProfile.class);
        Bundle b = new Bundle();
        b.putString("userId", usr_data.getId());
        b.putString("teamId", usr_data.getRoom());
        i.putExtra("data",b);
        startActivity(i);

    }

    public void getProject() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = database.getReference("Teams");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Team t= snapshot.getValue(Team.class);
                        if (t.getId().equals(teamId)) {
                            projectName = t.getName();
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
}
