package com.example.wawandco.scrumeet;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by wawandco on 11/7/17.
 */

public class Data {

    private static DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference();
    private static String usersReference ="Users";
    private static String teamsReference ="Teams";
    private static String updatesReference ="UserUpdates";


    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Team> teams = new ArrayList<>();
    private static ArrayList<StatusUpdate> status = new ArrayList<>();



    public static void saveUser(User usr){
        databaseReference.child(usersReference).child(usr.getId()).setValue(usr);
    }

    public static ArrayList<User> getUsers(){
        return users;
    }

    public static void setUser(ArrayList<User> usr){
        users=usr;
    }

    public static String getId(){
        return databaseReference.push().getKey();
    }

    public static void updateUser(User usr ){
        databaseReference.child(usersReference).child(usr.getId()).setValue(usr);
    }
    public static void delete(User usr){
        databaseReference.child(usersReference).child(usr.getId()).removeValue();
    }

//=========================== Team Dataase functions ================================

    public static void saveTeam(Team team){
        databaseReference.child(teamsReference).child(team.getId()).setValue(team);
    }

    public static ArrayList<Team> getTeam(){
        return teams;
    }

    public static void setTeam(ArrayList<Team> team){
        teams=team;
    }

    public static String getTeamId(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100000);
        return ""+randomInt;
    }

    public static void updateTeam(Team team ){
        databaseReference.child(teamsReference).child(team.getId()).setValue(team);
    }
    public static void deleteTeam(Team team){
        databaseReference.child(teamsReference).child(team.getId()).removeValue();
    }


//=========================== StatusUpdates Database functions ================================

    public static void saveStatusUpdate(StatusUpdate status){
        databaseReference.child(updatesReference).child(status.getId()).setValue(status);
    }

    public static ArrayList<StatusUpdate> getStatus(){
        return status;
    }

    public static void setStatus(ArrayList<StatusUpdate> status){
        status=status;
    }

    public static String getStatusId(){
        return databaseReference.push().getKey();
    }



    public static int getPhoto (){
        ArrayList<Integer> photos;

        photos = new ArrayList<>();
        photos.add(R.drawable.u1);
        photos.add(R.drawable.u2);
        photos.add(R.drawable.u3);

        int selectedPhoto;
        Random r = new Random();
        selectedPhoto = r.nextInt(photos.size());
        return photos.get(selectedPhoto);

    }

}


