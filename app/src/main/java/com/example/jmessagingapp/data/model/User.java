package com.example.jmessagingapp.data.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String id, name, email, password, gender;

    public User(String id, String name, String email, String password, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();

        map.put("name", this.name);
        map.put("email", this.email);
        map.put("password", this.password);
        map.put("gender", this.gender);

        return map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
