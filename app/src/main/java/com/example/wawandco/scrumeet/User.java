package com.example.wawandco.scrumeet;

import java.util.ArrayList;

/**
 * Created by wawandco on 11/7/17.
 */

public class User {
    private String id;
    private String email;
    private Boolean isManager;
    private String room;

    private String photo;
    private String cc;
    private String name;
    private int genere;
    private int shirtSize;
    private int shoesSize;


    private int pantsSize;
    private String project;
    private ArrayList<String> hobbies;
    private ArrayList<String> movies;
    private ArrayList<String> music;
    private ArrayList<String> food;
    private ArrayList<String> tastes;
    private ArrayList<String> goalsDone;

    public User(){
    }


    public User(String id, String room,Boolean isManager, String name ,String email, String photo ) {
        this.id = id;
        this.isManager = isManager;
        this.room = room;
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public User(String id, String photo, String cc, String name, int genere, int shirtSize, int shoesSize, int pantsSize, String project, ArrayList<String> hobbies, ArrayList<String> movies, ArrayList<String> music, ArrayList<String> food, ArrayList<String> tastes, ArrayList<String> goalsDone) {
        this.id = id;
        this.photo = photo;
        this.cc = cc;
        this.name = name;
        this.genere = genere;
        this.shirtSize = shirtSize;
        this.shoesSize = shoesSize;
        this.pantsSize = pantsSize;
        this.project = project;
        this.hobbies = hobbies;
        this.movies = movies;
        this.music = music;
        this.food = food;
        this.tastes = tastes;
        this.goalsDone = goalsDone;
    }

    public User(String id, String roomKey, Boolean isManagerUser, String email) {
        this.id = id;
        this.room = roomKey;
        this.isManager = isManagerUser;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGenere() {
        return genere;
    }

    public void setGenere(int genere) {
        this.genere = genere;
    }

    public int getShirtSize() {
        return shirtSize;
    }

    public void setShirtSize(int shirtSize) {
        this.shirtSize = shirtSize;
    }

    public int getShoesSize() {
        return shoesSize;
    }

    public void setShoesSize(int shoesSize) {
        this.shoesSize = shoesSize;
    }

    public int getPantsSize() {
        return pantsSize;
    }

    public void setPantsSize(int pantsSize) {
        this.pantsSize = pantsSize;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public ArrayList<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<String> hobbies) {
        this.hobbies = hobbies;
    }

    public ArrayList<String> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<String> movies) {
        this.movies = movies;
    }

    public ArrayList<String> getMusic() {
        return music;
    }

    public void setMusic(ArrayList<String> music) {
        this.music = music;
    }

    public ArrayList<String> getFood() {
        return food;
    }

    public void setFood(ArrayList<String> food) {
        this.food = food;
    }

    public ArrayList<String> getTastes() {
        return tastes;
    }

    public void setTastes(ArrayList<String> tastes) {
        this.tastes = tastes;
    }

    public ArrayList<String> getGoalsDone() {
        return goalsDone;
    }

    public void setGoalsDone(ArrayList<String> goalsDone) {
        this.goalsDone = goalsDone;
    }

    public void guardar(){Data.saveUser(this);}

    public void modificar(){Data.update(this);}

    public void eliminar(){Data.delete(this);}

}
