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
    private String birthday;
    private String shirtSize;
    private String shoesSize;


    private String pantsSize;
    private String project;

    private String hobbies;
    private String movies;
    private String music;
    private String food;
    private String tastes;
    private String goalsDone;
    private String country;

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

    public User(String id, String email, Boolean isManager, String room, String photo, String cc, String name, String birthday, String shirtSize, String shoesSize, String pantsSize, String project, String hobbies, String movies, String music, String food, String tastes, String goalsDone, String country) {
        this.id = id;
        this.email = email;
        this.isManager = isManager;
        this.room = room;
        this.photo = photo;
        this.cc = cc;
        this.name = name;
        this.birthday = birthday;
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
        this.country = country;
    }

    public User(String id, String photo, String cc, String name, String birthday, String country, String shirtSize, String shoesSize, String pantsSize, String project, String hobbies, String movies, String music, String food, String tastes, String goalsDone) {
        this.id = id;
        this.photo = photo;
        this.cc = cc;
        this.name = name;
        this.birthday = birthday;
        this.country = country;
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

    public String getBirthday() {
        return birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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


    public String getShirtSize() {
        return shirtSize;
    }

    public void setShirtSize(String shirtSize) {
        this.shirtSize = shirtSize;
    }

    public String getShoesSize() {
        return shoesSize;
    }

    public void setShoesSize(String shoesSize) {
        this.shoesSize = shoesSize;
    }

    public String getPantsSize() {
        return pantsSize;
    }

    public void setPantsSize(String pantsSize) {
        this.pantsSize = pantsSize;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getMovies() {
        return movies;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getTastes() {
        return tastes;
    }

    public void setTastes(String tastes) {
        this.tastes = tastes;
    }

    public String getGoalsDone() {
        return goalsDone;
    }

    public void setGoalsDone(String goalsDone) {
        this.goalsDone = goalsDone;
    }

    public void guardar(){Data.saveUser(this);}

    public void update(){Data.updateUser(this);}

    public void eliminar(){Data.delete(this);}

}
