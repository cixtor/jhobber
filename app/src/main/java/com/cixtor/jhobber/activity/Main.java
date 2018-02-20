package com.cixtor.jhobber.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cixtor.jhobber.R;
import com.cixtor.jhobber.fragment.About;
import com.cixtor.jhobber.fragment.Home;
import com.cixtor.jhobber.fragment.Planet;
import com.cixtor.jhobber.fragment.Profile;
import com.cixtor.jhobber.model.DownloadImageTask;
import com.cixtor.jhobber.model.Job;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main extends Base implements
        About.OnFragmentInteractionListener,
        Home.OnFragmentInteractionListener,
        Planet.OnFragmentInteractionListener,
        Profile.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private final int NAVIGATION_HEADER_VIEW = 0;

    private NavigationView mDrawerView;
    private DrawerLayout mDrawerLayout;
    private Menu nvDrawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set a Toolbar to replace the ActionBar. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Find and attach a toggle event to our Drawer view. */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mDrawerView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerView.setNavigationItemSelectedListener(this);
        nvDrawerMenu = mDrawerView.getMenu();

        this.setInitialFragment();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.topbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logoutButton) {
            this.resetProfileData(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_home) {
            fragment = new Home();
        } else if (id == R.id.nav_map) {
            fragment = new Planet();
        } else if (id == R.id.nav_profile) {
            fragment = new Profile();
        } else if (id == R.id.nav_about) {
            fragment = new About();
        }

        /* Fragment content switching. */
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContent, fragment)
                    .commit();
        }

        /* Close the drawer after touching an item. */
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String title) {
        /* Replace the toolbar title based on current fragment. */
        getSupportActionBar().setTitle(title);
    }

    private void setInitialFragment() {
        Fragment fragment;

        this.setDrawerUserData();

        if (this.isValidAccount()) {
            /* account exists; show profile */
            mDrawerView.setCheckedItem(R.id.nav_profile);

            nvDrawerMenu.findItem(R.id.nav_home).setEnabled(false);
            nvDrawerMenu.findItem(R.id.nav_map).setEnabled(true);
            nvDrawerMenu.findItem(R.id.nav_profile).setEnabled(true);
            nvDrawerMenu.findItem(R.id.nav_about).setEnabled(true);
        } else {
            /* account does not exists; show signup */
            mDrawerView.setCheckedItem(R.id.nav_home);

            nvDrawerMenu.findItem(R.id.nav_home).setEnabled(true);
            nvDrawerMenu.findItem(R.id.nav_map).setEnabled(false);
            nvDrawerMenu.findItem(R.id.nav_profile).setEnabled(false);
            nvDrawerMenu.findItem(R.id.nav_about).setEnabled(true);
        }

        if (this.isValidAccount()) {
            fragment = new Profile();
        } else {
            fragment = new Home();
        }

        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, fragment)
                .commit();
    }

    private void setDrawerUserData() {
        View v = mDrawerView.getHeaderView(NAVIGATION_HEADER_VIEW);

        TextView tvFullname = (TextView) v.findViewById(R.id.drawerFullname);
        TextView tvOccupation = (TextView) v.findViewById(R.id.drawerOccupation);
        ImageView tvAvatar = (ImageView) v.findViewById(R.id.drawerAvatar);

        if (!this.isValidAccount()) {
            tvFullname.setText("");
            tvOccupation.setText("");
            tvAvatar.setImageResource(R.mipmap.ic_launcher_round);
            return;
        }

        String avatar = this.getUserAccount().getAvatar();

        tvFullname.setText(this.getUserAccount().getFullName());

        tvOccupation.setText(this.getUserAccount().getOccupation());

        new DownloadImageTask(this, tvAvatar).execute(avatar);
    }

    public void enableAdvancedFeatures() {
        /* expect fragment.profile */
        this.setInitialFragment();
    }

    public void resetProfileData(final Main here) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout_title)
                .setMessage(R.string.logout_warning)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        here.deleteUserAccount();
                        here.setInitialFragment();
                        here.alert(getString(R.string.logout_success));
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    public ArrayList<Job> collectJobsData(JSONObject res) throws JSONException {
        ArrayList<Job> jobs = new ArrayList<Job>();
        JSONArray items = res.getJSONArray("jobs");
        int total = items.length();

        for (int i = 0; i < total; i++) {
            Job job = new Job();

            JSONObject item = items.getJSONObject(i);

            job.setID(item.getString("id"));
            job.setCompany(item.getString("company"));
            job.setTitle(item.getString("title"));
            job.setSkills(item.getString("skills"));
            job.setImage(item.getString("image"));
            job.setLongitude(item.getDouble("longitude"));
            job.setLatitude(item.getDouble("latitude"));

            jobs.add(job);
        }

        return jobs;
    }

    public void hideKeyboard(TextView tv) {
        /* hide the keyboard to increase the visibility of the Snackbar alerts */
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
    }

    public void alert(String message) {
        Snackbar.make(
                this.findViewById(R.id.flContent),
                message.toString(),
                Snackbar.LENGTH_SHORT
        ).show();
    }
}
