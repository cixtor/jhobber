package com.cixtor.jhobber.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.UUID;

public class Base extends AppCompatActivity {
    public final String TAG = "JHOBBER_BASE";
    public final String WEB_SERVICE = "https://d719134d.ngrok.io";

    String deviceID;
    boolean accountExists;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.deviceID = UUID.randomUUID().toString();

        this.accountExists = false; /* assume new user */

        this.requestQueue = Volley.newRequestQueue(this);
    }
}
