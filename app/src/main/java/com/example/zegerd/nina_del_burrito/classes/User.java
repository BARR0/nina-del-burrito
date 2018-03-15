package com.example.zegerd.nina_del_burrito.classes;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Zegerd on 2/7/2018.
 */

@IgnoreExtraProperties
public class User {
    private String username;
    private int rolId; // 0 client, 1 seller

    public User(){

    }

    public User(String name, int id) {
        this.username = name;
        this.rolId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }
}
