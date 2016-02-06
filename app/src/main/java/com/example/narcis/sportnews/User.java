package com.example.narcis.sportnews;

/**
 * Created by Narcis on 2/6/2016.
 */
public class User {
    String name, username, password;
    int age;

    public User (String name, int age, String username, String password) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.age = -1;
        this.name = "";
    }
}
