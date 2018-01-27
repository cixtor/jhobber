package com.cixtor.jhobber.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cixtor.jhobber.R;

public class Splash extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private final int LAUNCH_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, Main.class);
                startActivity(i);
            }
        }, LAUNCH_TIMEOUT);
    }
}
