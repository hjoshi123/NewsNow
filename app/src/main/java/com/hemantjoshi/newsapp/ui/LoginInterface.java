package com.hemantjoshi.newsapp.ui;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hemantjoshi.newsapp.model.User;

/**
 * Created by HemantJ on 24/10/17.
 */

public interface LoginInterface {
    void getAuthWithGoogle(GoogleSignInResult result);
    User processLoginDetails(User details);
}
