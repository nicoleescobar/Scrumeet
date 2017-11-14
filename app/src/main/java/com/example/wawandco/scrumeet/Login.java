package com.example.wawandco.scrumeet;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity  implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "EmailPassword";
    private ArrayList<Team> teams;
    private TextView mStatusTextView;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Switch isManagerBtn;
    private boolean isManager;
    private EditText room;
    private DatabaseReference databaseReference;
    private final String BD="Teams";

    private Resources resources;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            databaseReference = FirebaseDatabase.getInstance().getReference();
            teams = new ArrayList<>();

            mStatusTextView = (TextView)findViewById(R.id.status);
            mEmailField = (EditText)findViewById(R.id.field_email);
            mPasswordField = (EditText)findViewById(R.id.field_password);
            isManagerBtn =  (Switch)findViewById(R.id.isManager);
            room = (EditText)findViewById(R.id.roomTxt);
            resources = this.getResources();
            findViewById(R.id.email_sign_in_button).setOnClickListener(this);
            findViewById(R.id.email_create_account_button).setOnClickListener(this);
            isManagerBtn.setOnCheckedChangeListener(this);


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
            super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void createAccount(String email, String password) {

            if (!validateForm()) {
                    return;
            }


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser usr= FirebaseAuth.getInstance().getCurrentUser();
                                        saveUser(usr);
                                    } else {
                                         Toast.makeText(Login.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                                    }
                            }
                    });
    }

    private void signIn(String email, String password) {
                if (!validateForm()) {
                        return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                        goToNextPage();
                                } else {
                                        Toast.makeText(Login.this, R.string.auth_failed,Toast.LENGTH_SHORT).show();
                                        mStatusTextView.setText(R.string.auth_failed);
                                }
                        }
                });
        }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
                mEmailField.setError(resources.getString(R.string.required));
                valid = false;
        } else {
                mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
                mPasswordField.setError(resources.getString(R.string.required));
                valid = false;
        } else {
                mPasswordField.setError(null);
        }

        String roomKey = room.getText().toString();
        if (TextUtils.isEmpty(roomKey) && !isManager) {
            room.setError(resources.getString(R.string.required));
            valid = false;
        } else {
            room.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.email_create_account_button) {
                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            } else if (i == R.id.email_sign_in_button) {
                    signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
    }

    private void saveUser(FirebaseUser user) {

        if (user != null) {
            String id = Data.getId();
            String email = user.getEmail();
            String roomKey = room.getText().toString();
            Boolean isManagerUser = isManager;

            if (isManagerUser) {roomKey="";}
            User usr = new User(id, roomKey, isManagerUser, email.toString());

            usr.guardar();
            goToNextPage();
        }

    }

    private void goToNextPage() {
        if (isManager) {
            Intent i = new Intent(Login.this,TeamList.class);
            startActivity(i);
        } else {
            String room_id = room.getText().toString();
            validateTeam(room_id);

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        isManager = isChecked;
    }

    public void validateTeam(final String room_id){

        databaseReference.child(BD).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                teams.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Team t= snapshot.getValue(Team.class);
                        Log.e("console", "id:"+t.getId()+"---"+room_id);
                        if (t.getId().equals(room_id)) {
                            Log.e("console", "VALID");
                            Intent i = new Intent(Login.this, TeamHome.class);
                            Bundle b = new Bundle();
                            b.putString("teamId",room_id);
                            i.putExtra("data",b);
                            startActivity(i);
                            Toast.makeText(Login.this, R.string.auth,Toast.LENGTH_SHORT).show();
                            mStatusTextView.setText("");
                            room.setError(null);
                        } else {
                            Toast.makeText(Login.this, R.string.auth_failed,Toast.LENGTH_SHORT).show();
                            mStatusTextView.setText(R.string.auth_failed);
                            room.setError(resources.getString(R.string.invalid));

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}

