package com.example.wawandco.scrumeet;

/**
 * Created by wawandco on 11/13/17.
 */

public class StatusUpdate {
    private String id;
    private String userId;
    private String username;
    private String teamId;
    private String did;
    private String willdo;
    private String blockers;

    public StatusUpdate(){

    }

    public StatusUpdate(String id, String userId, String username, String teamId, String did, String willdo, String blockers) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.teamId = teamId;
        this.did = did;
        this.willdo = willdo;
        this.blockers = blockers;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getWilldo() {
        return willdo;
    }

    public void setWilldo(String willdo) {
        this.willdo = willdo;
    }

    public String getBlockers() {
        return blockers;
    }

    public void setBlockers(String blockers) {
        this.blockers = blockers;
    }
}
