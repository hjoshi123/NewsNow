package com.hemantjoshi.newsapp.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * @author HemantJ on 24/10/17.
 * @presenter for LoginActivity
 */

public class LoginPresenter implements LoginInterface {
    private FirebaseAuth mAuth;
    private LoginActivity activity;

    /**
     * Constructor for the presenter class
     * @param mAuth FirebaseAuth
     * @param activity for getting the activity context
     */
    LoginPresenter(FirebaseAuth mAuth,LoginActivity activity){
        this.mAuth = mAuth;
        this.activity = activity;
    }

    /**
     * This method takes in the details of the authenticated user like email and user avatar etc and uses
     * it to start a new activity with the user signed in.
     */
    @Override
    public void getAuthWithGoogle(final GoogleSignInResult result) {
        if(result.isSuccess()){
            final GoogleSignInAccount account = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String email = task.getResult().getUser().getEmail();
                                String dispName = task.getResult().getUser().getDisplayName();
                                activity.startMainActivity();
                            }else{

                            }
                        }
                    });
        }
    }
}
