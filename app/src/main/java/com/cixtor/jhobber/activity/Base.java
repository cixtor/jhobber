package com.cixtor.jhobber.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cixtor.jhobber.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Base extends AppCompatActivity {
    public final int LOADING_TIME = 1000;
    public final String TAG = "JHOBBER";
    public final String DATABASE = "com.cixtor.jhobber.prefs";
    public final String WEB_SERVICE = "https://ee473f7d.ngrok.io";

    private User userAccount;
    private RequestQueue requestQueue;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.userAccount = new User();
        this.requestQueue = Volley.newRequestQueue(this);
        this.mPreferences = getSharedPreferences(DATABASE, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();

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
        HashMap<String, String> data = new HashMap<String, String>();

        data.put("uuid", this.mPreferences.getString("uuid", ""));
        data.put("firstname", this.mPreferences.getString("firstname", ""));
        data.put("lastname", this.mPreferences.getString("lastname", ""));
        data.put("occupation", this.mPreferences.getString("occupation", ""));
        data.put("avatar", this.mPreferences.getString("avatar", ""));

        this.setUserAccount(new JSONObject(data));
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

    public void saveUserAccount(JSONObject obj) throws JSONException {
        this.setUserAccount(obj);

        mEditor.putString("uuid", this.userAccount.getUUID());
        mEditor.putString("firstname", this.userAccount.getFirstName());
        mEditor.putString("lastname", this.userAccount.getLastName());
        mEditor.putString("occupation", this.userAccount.getOccupation());
        mEditor.putString("avatar", this.userAccount.getAvatar());

        mEditor.commit();
    }

    public void deleteUserAccount() {
        this.userAccount = new User();

        mEditor.clear();
        mEditor.commit();
    }

    public void log(String msg) {
        Log.d(TAG, msg);
    }
}
