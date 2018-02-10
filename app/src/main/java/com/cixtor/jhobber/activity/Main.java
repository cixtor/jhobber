package com.cixtor.jhobber.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cixtor.jhobber.R;
import com.cixtor.jhobber.fragment.About;
import com.cixtor.jhobber.fragment.Home;
import com.cixtor.jhobber.fragment.Planet;
import com.cixtor.jhobber.fragment.Profile;
import com.cixtor.jhobber.fragment.Settings;

public class Main extends Base implements
        About.OnFragmentInteractionListener,
        Home.OnFragmentInteractionListener,
        Planet.OnFragmentInteractionListener,
        Profile.OnFragmentInteractionListener,
        Settings.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private NavigationView nvDrawer;
    private Menu nvDrawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set a Toolbar to replace the ActionBar. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Find and attach a toggle event to our Drawer view. */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        nvDrawer.setNavigationItemSelectedListener(this);
        nvDrawerMenu = nvDrawer.getMenu();

        this.setInitialFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
        if (id == R.id.action_settings) {
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
        } else if (id == R.id.nav_settings) {
            fragment = new Settings();
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String title) {
        /* Replace the toolbar title based on current fragment. */
        getSupportActionBar().setTitle(title);
    }

    private void setInitialFragment() {
        Fragment fragment;

        if (this.isValidAccount()) {
            /* account exists; show profile */
            nvDrawer.setCheckedItem(R.id.nav_profile);

            nvDrawerMenu.findItem(R.id.nav_home).setEnabled(false);
            nvDrawerMenu.findItem(R.id.nav_map).setEnabled(true);
            nvDrawerMenu.findItem(R.id.nav_profile).setEnabled(true);
            nvDrawerMenu.findItem(R.id.nav_settings).setEnabled(true);
            nvDrawerMenu.findItem(R.id.nav_about).setEnabled(true);
        } else {
            /* account does not exists; show signup */
            nvDrawer.setCheckedItem(R.id.nav_home);

            nvDrawerMenu.findItem(R.id.nav_home).setEnabled(true);
            nvDrawerMenu.findItem(R.id.nav_map).setEnabled(false);
            nvDrawerMenu.findItem(R.id.nav_profile).setEnabled(false);
            nvDrawerMenu.findItem(R.id.nav_settings).setEnabled(false);
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

    public void enableAdvancedFeatures() {
        /* expect fragment.profile */
        this.setInitialFragment();
    }

    public void alert(String message) {
        Snackbar.make(
                this.findViewById(R.id.flContent),
                message.toString(),
                Snackbar.LENGTH_SHORT
        ).show();
    }
}
