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
    public final String WEB_SERVICE = "https://19ba868f.ngrok.io";

    private User userAccount;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.userAccount = new User();

        this.requestQueue = Volley.newRequestQueue(this);

        try {
            this.loadLocalData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidAccount() {
        return this.userAccount.isValid();
    }

    public User getUserAccount() {
        return this.userAccount;
    }

    public void addRequestToQueue(JsonObjectRequest obj) {
        this.requestQueue.add(obj);
    }

    private void loadLocalData() throws JSONException {
        String content = this.getLocalDataContent();
        JSONObject obj = new JSONObject(content);
        this.setUserAccount(obj);
    }

    public void setUserAccount(JSONObject obj) throws JSONException {
        /* database is corrupt */
        if (!obj.has("uuid")) {
            return;
        }

        this.userAccount.setUUID(obj.getString("uuid"));

        /* stop if the UUID is invalid */
        if (!this.userAccount.isValid()) {
            return;
        }

        this.userAccount.setFirstName(obj.getString("firstname"));
        this.userAccount.setLastName(obj.getString("lastname"));
        this.userAccount.setOccupation(obj.getString("occupation"));
        this.userAccount.setAvatar(obj.getString("avatar"));
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
