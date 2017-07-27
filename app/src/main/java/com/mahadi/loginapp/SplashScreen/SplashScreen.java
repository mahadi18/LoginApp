package com.mahadi.loginapp.SplashScreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mahadi.loginapp.Dashboard.DashboardActivity;
import com.mahadi.loginapp.Login.LoginActivity;
import com.mahadi.loginapp.Login.RegisterHandler;
import com.mahadi.loginapp.Login.Userloginsession;
import com.mahadi.loginapp.R;
import com.mahadi.loginapp.*;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Userloginsession login = new Userloginsession(getApplicationContext());

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (login.isuserLoggedIn()) {
                    startActivity(new Intent(SplashScreen.this, DashboardActivity.class));

                    finish();


                } else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));

                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}