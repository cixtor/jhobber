package com.cixtor.jhobber.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cixtor.jhobber.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends Base {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        JsonObjectRequest obj = new JsonObjectRequest(
                Request.Method.GET,
                WEB_SERVICE + "/account",
                null,
                this.getUserOnResponse(this),
                this.getUserResponseListener()
        );

        this.requestQueue.add(obj);
    }

    public Response.Listener<JSONObject> getUserOnResponse(final Base parent) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    parent.accountExists = res.getBoolean("ok");
                    Intent i = new Intent(Splash.this, Main.class);
                    parent.startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Response.ErrorListener getUserResponseListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
            }
        };
    }
}
