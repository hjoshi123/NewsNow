package com.hemantjoshi.newsapp.ui;

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
import com.hemantjoshi.newsapp.model.User;

/**
 * Created by HemantJ on 24/10/17.
 */

public class LoginPresenter implements LoginInterface {
    private Context mContext;
    private FirebaseAuth mAuth;
    private LoginActivity activity;

    LoginPresenter(Context context, FirebaseAuth mAuth,LoginActivity activity){
        mContext = context;
        this.mAuth = mAuth;
        this.activity = activity;
    }

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
                                processLoginDetails(new User(email,dispName));
                                activity.startMainActivity();
                            }else{

                            }
                        }
                    });
        }
    }

    @Override
    public User processLoginDetails(User details) {
        return details;
    }
}
