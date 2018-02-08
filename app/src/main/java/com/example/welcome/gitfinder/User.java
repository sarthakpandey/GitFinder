package com.example.welcome.gitfinder;

/**
 * Created by WELCOME on 05-10-2017.
 */

public class User {

    int id;
    String type;
    String name;
    int followers;
    int following;
    String location;
    String login;

    public User(int id, String type, String name, int followers, int following, String location) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.followers = followers;
        this.following = following;
        this.location = location;
    }
}
