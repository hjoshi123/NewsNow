package com.hemantjoshi.newsapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.hemantjoshi.newsapp.R;

public class SplashActivity extends AppCompatActivity {
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
