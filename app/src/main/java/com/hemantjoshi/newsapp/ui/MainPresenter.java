package com.hemantjoshi.newsapp.ui;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by HemantJ on 28/10/17.
 */

public class MainPresenter implements MainInterface {
    private FirebaseAuth mAuth;
    private TextView navName, navEmail;

    MainPresenter(FirebaseAuth mAuth,TextView navName, TextView navEmail){
        this.mAuth = mAuth;
        this.navEmail = navEmail;
        this.navName = navName;
    }

    @Override
    public void setUserData() {
        if(mAuth.getCurrentUser() != null){
            navName.setText(mAuth.getCurrentUser().getDisplayName());
            navEmail.setText(mAuth.getCurrentUser().getEmail());
        }
    }
}
