package com.hemantjoshi.newsapp.splash;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hemantjoshi.newsapp.R;
import com.hemantjoshi.newsapp.login.LoginActivity;
import com.hemantjoshi.newsapp.newsmain.MainActivity;

/**
 * @author HemantJ
 * SplashActivity is the {@link android.app.LauncherActivity} that checks if the user is logged in or not
 * and redirects the user accordingly
 */
public class SplashActivity extends AppCompatActivity {
    private static final int ACCESS_FINE_LOC = 1;
    private FirebaseAuth mAuth;
    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        splashPresenter = new SplashPresenter(mAuth,SplashActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        splashPresenter.subscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashPresenter.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashPresenter.unsubscribe();
    }

    public void startLoginActivity(){
        Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void startMainActivity(){
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
    }
}
