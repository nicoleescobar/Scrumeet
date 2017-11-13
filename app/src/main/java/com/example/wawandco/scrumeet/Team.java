package com.example.wawandco.scrumeet;

/**
 * Created by wawandco on 11/9/17.
 */

public class Team {
    private String id;
    private String name;
    private String manager;
    private Boolean isActive;
    private int membersCount;

    public Team(){

    }

    public Team(String id, String name,String manager, Boolean isActive, int membersCount) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.isActive = isActive;
        this.membersCount = membersCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public void saveNewTeam(){Data.saveTeam(this);}

    public void update(){Data.updateTeam(this);}

    public void delete(){Data.deleteTeam(this);}
}
