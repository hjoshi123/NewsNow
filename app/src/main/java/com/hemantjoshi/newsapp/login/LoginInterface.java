package com.hemantjoshi.newsapp.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.Task;

/**
 * Created by HemantJ on 24/10/17.
 */

public interface LoginInterface {
    /**
     * @param result contains the signed in details of the user
     */
    void getAuthWithGoogle(GoogleSignInAccount result);
}
