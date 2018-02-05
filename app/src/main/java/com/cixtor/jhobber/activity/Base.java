package com.cixtor.jhobber.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cixtor.jhobber.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Base extends AppCompatActivity {
    public final int LOADING_TIME = 1000;
    public final String DATABASE = "jhobber.database.json";
    public final String WEB_SERVICE = "https://d719134d.ngrok.io";

    private User userAccount;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.userAccount = this.loadLocalData();

        this.requestQueue = Volley.newRequestQueue(this);
    }

    public boolean isValidAccount() {
        return this.userAccount.getUUID() != "";
    }

    public String getUserUUID() {
        return this.userAccount.getUUID();
    }

    public void addRequestToQueue(JsonObjectRequest obj) {
        this.requestQueue.add(obj);
    }

    private User loadLocalData() {
        User user = new User();

        try {
            String content = this.getLocalDataContent();

            JSONObject obj = new JSONObject(content);

            /* database is corrupt */
            if (!obj.has("uuid")) {
                return user;
            }

            user.setUUID(obj.getString("uuid"));
            user.setFirstName(obj.getString("firstname"));
            user.setLastName(obj.getString("lastname"));
            user.setOccupation(obj.getString("occupation"));
            user.setAvatar(obj.getString("avatar"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean hasLocalStorage() {
        AssetManager mg = this.getResources().getAssets();

        try {
            List files = Arrays.asList(mg.list(""));
            return files.contains(this.DATABASE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getLocalDataContent() {
        if (!this.hasLocalStorage()) {
            return "{}";
        }

        String content = "";

        try {
            String line;
            AssetManager mg = this.getResources().getAssets();
            InputStream is = mg.open(this.DATABASE);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(isr);

            while ((line = reader.readLine()) != null) {
                content = content + line;
            }

            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
