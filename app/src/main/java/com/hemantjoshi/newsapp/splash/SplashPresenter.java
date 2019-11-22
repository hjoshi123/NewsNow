package com.hemantjoshi.newsapp.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hemantjoshi.newsapp.appintro.CustomIntro;

/**
 * @author HemantJ on 25/10/17.
 * @presenter for SplashActivity
 */

public class SplashPresenter implements SplashInterface {
    private FirebaseAuth mAuth;
    private SplashActivity activity;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    SplashPresenter(FirebaseAuth auth,SplashActivity activity){
        this.mAuth = auth;
        this.activity = activity;
    }

    /**
     * {@code Thread t } launches the {@link com.github.paolorotolo.appintro.AppIntro2} if its not seen
     * by the user in the first time.
     * Else it adds FirebaseAuthStateListener to check if the user is logged in
     * called in {@code onStart()} {@code onResume()}
     * @return void
     */
    @Override
    public void subscribe() {
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final SharedPreferences pref1 = PreferenceManager
                        .getDefaultSharedPreferences(activity);

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = pref1.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    final Intent i = new Intent(activity, CustomIntro.class);

                    activity.runOnUiThread(new Runnable() {
                        @Override public void run() {
                            activity.startActivity(i);
                        }
                    });

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = pref1.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    if(t.getState() == Thread.State.NEW){
                        t.start();
                    }
                    activity.startLoginActivity();
                }else{
                    activity.startMainActivity();
                }
            }
        };

        mAuth.addAuthStateListener(mAuthStateListener);
    }

    /**
     * This method removes the auth state listener
     * called in {@code onDestroy()} and {@code onPause()}
     */
    @Override
    public void unsubscribe() {
        if(mAuthStateListener != null)
            mAuth.removeAuthStateListener(mAuthStateListener);
    }
}
