package com.cixtor.jhobber.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cixtor.jhobber.R;

public class Splash extends Base {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, Main.class));
            }
        }, this.LOADING_TIME);
    }
}
