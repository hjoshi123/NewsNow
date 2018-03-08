package com.hemantjoshi.newsapp.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.hemantjoshi.newsapp.R;
import com.hemantjoshi.newsapp.newsmain.MainActivity;

/**
 * @author HemantJ
 * Activity for logging in the user from his google account
 */

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private Toolbar toolbar;
    private SignInButton signInButton;
    private final int RC_SIGN_IN = 123;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    private LoginPresenter presenter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");


        signInButton = findViewById(R.id.googleSignIn);
        signInButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        presenter = new LoginPresenter(mAuth,LoginActivity.this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.googleSignIn){
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(intent,RC_SIGN_IN);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Couldn't connect to internet.\n Please try again", Toast.LENGTH_SHORT).show();
    }

    /**
     * Callback after the logging in/error takes place
     * @param requestCode requestCode from the log in activity of Google
     * @param resultCode resultCode RESULT_OK or not
     * @param data signin details
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            presenter.getAuthWithGoogle(result);
        }
    }

    public void startMainActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }
}
