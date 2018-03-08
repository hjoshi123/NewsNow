package com.hemantjoshi.newsapp.login;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by HemantJ on 24/10/17.
 */

public interface LoginInterface {
    /**
     * @param result contains the signed in details of the user
     */
    void getAuthWithGoogle(GoogleSignInResult result);
}
