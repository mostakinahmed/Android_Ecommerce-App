package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Transaction;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser= firebaseAuth.getCurrentUser();

        setContentView(R.layout.splash_activity);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                } finally {
                    if (currentUser == null){
                    Intent openMainActivity = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(openMainActivity );
                    SplashActivity.this.finish();
                } else{

                        Intent openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(openMainActivity );
                        SplashActivity.this.finish();
                    }
                }
            }
        };
        timer.start();
}


/*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser= firebaseAuth.getCurrentUser();
        if (currentUser == null){
            Intent Registerintent = new Intent(SplashActivity.this,RegisterActivity.class);
            startActivity(Registerintent);
            finish();
        }else{
            Intent Mainintent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(Mainintent);
            finish();

        }

    }  */
}