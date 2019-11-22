package com.hemantjoshi.newsapp.login;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hemantjoshi.newsapp.R;
import com.hemantjoshi.newsapp.newsmain.MainActivity;

/**
 * @author HemantJ
 * Activity for logging in the user from his google account
 */

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener  {
    private Toolbar toolbar;
    private SignInButton signInButton;
    private final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
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
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.googleSignIn){
            Log.d("LoginActivity", "Enterred here");
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent,RC_SIGN_IN);
        }
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
            try {
                Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
                final GoogleSignInAccount account = (GoogleSignInAccount) task.getResult(ApiException.class);
                presenter.getAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.d("LoginActivity", e.getMessage());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public void startMainActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }
}
