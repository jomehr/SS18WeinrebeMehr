package com.example.jan.kassenzettel_scan.data;

import java.io.Serializable;

public class UserData implements Serializable{

    private static final String TAG = "UserData";

    private String id, name, email, groupId;
    private double defaultParticipation;

    public UserData(String userId, String name, String groupId) {
        this.id = userId;
        this.name = name;
        this.groupId = groupId;
    }

    public String getUserId() {
        return id;
    }

    public String getUserName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGroupId() {
        return groupId;
    }

    public double getDefaultParticipation() {
        return defaultParticipation;
    }

    public void setUserId(String id) {
        this.id = id;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setDefaultParticipation(double participation) {
        this.defaultParticipation = participation;
    }
}
