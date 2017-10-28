package com.hemantjoshi.newsapp.model;

/**
 * Created by HemantJ on 24/10/17.
 */

public class User {
    private String userName;
    private String email;

    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getEmail(){
        return this.email;
    }
}
