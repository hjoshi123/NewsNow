package com.hemantjoshi.newsapp.ui;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by HemantJ on 25/10/17.
 */

public class SplashPresenter implements SplashInterface {
    private FirebaseAuth mAuth;
    private SplashActivity activity;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    SplashPresenter(FirebaseAuth auth,SplashActivity activity){
        this.mAuth = auth;
        this.activity = activity;
    }

    @Override
    public void subscribe() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    activity.startLoginActivity();
                }else{
                    activity.startMainActivity();
                }
            }
        };

        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void unsubscribe() {
        if(mAuthStateListener != null)
            mAuth.removeAuthStateListener(mAuthStateListener);
    }
}
