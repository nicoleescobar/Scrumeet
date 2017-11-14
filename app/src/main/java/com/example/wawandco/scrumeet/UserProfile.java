package com.example.wawandco.scrumeet;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    private Resources res;
    private FirebaseAuth mAuth;
    String userID;
    String teamId;

    private Bundle b;
    private Intent i;

    private TextView username , birthday, projectname, country, cc , tastes, shirt, shoes, pants, hobbies, food, movies, music;
    private CircleImageView goal1, goal2, goal3;
    String[] goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        res = this.getResources();
        mAuth = FirebaseAuth.getInstance();

        i = getIntent();
        b = i.getBundleExtra("data");

        if (b != null){
            teamId = b.get("teamId").toString();
            userID = b.get("userId").toString();
        }

        username = (TextView) findViewById(R.id.username_value);
        birthday = (TextView) findViewById(R.id.user_birthday);
        projectname = (TextView) findViewById(R.id.user_project_name);
        tastes = (TextView) findViewById(R.id.user_tastes_value);
        shirt = (TextView) findViewById(R.id.user_shirtSize_value);
        shoes = (TextView) findViewById(R.id.user_shoes_value);
        pants = (TextView) findViewById(R.id.user_pants_value);
        hobbies = (TextView) findViewById(R.id.user_hobbies_value);
        movies = (TextView) findViewById(R.id.user_movies_value);
        music = (TextView) findViewById(R.id.user_music_value);
        country = (TextView) findViewById(R.id.user_country);
        cc = (TextView) findViewById(R.id.user_cc_value);
        food = (TextView) findViewById(R.id.user_food_value);

        goal1 = (CircleImageView) findViewById(R.id.imgGoal1) ;
        goal2 = (CircleImageView) findViewById(R.id.imgGoal2) ;
        goal3 = (CircleImageView) findViewById(R.id.imgGoal3) ;



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
        projectname.setText(usr.getProject());
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

        if (usr.getGoalsDone() != null) {
            goals = usr.getGoalsDone().split(" , ");

            for (int i = 0; i > 3; i++){
                if (goals[i].equals("1")) {
                    Picasso.with(UserProfile.this).load(R.drawable.goal1).into(goal1);
                }

                if (goals[i].equals("2")) {
                    Picasso.with(UserProfile.this).load(R.drawable.goal2).into(goal3);
                }

                if (goals[i].equals("3")) {
                    Picasso.with(UserProfile.this).load(R.drawable.goal3).into(goal3);
                }

            }
        }




    }
}
