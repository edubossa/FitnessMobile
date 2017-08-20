package com.ews.fitnessmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ews.fitnessmobile.fragments.UnitsFragment;
import com.ews.fitnessmobile.model.Login;
import com.facebook.login.LoginManager;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_LOG = "MainActivity";
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (getIntent() != null) {
            this.login = getIntent().getParcelableExtra(LoginActivity.PUT_LOGIN);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new UnitsFragment())
                    .addToBackStack(null)
                    .commit();
        }

        addMenuItemInNavMenuDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
        }
    }

    private void addMenuItemInNavMenuDrawer() {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.d(TAG_LOG, "onNavigationItemSelected ID -->  " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.menuFitness:
                Log.d(TAG_LOG, "Menu Units Selected !");
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new UnitsFragment())
                    .addToBackStack(null)
                    .commit();
                break;
            case R.id.menuAbout:
                Log.d(TAG_LOG, "Menu About Selected !");
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.menuExit:
                LoginManager.getInstance().logOut();
                Log.d(TAG_LOG, "Menu Exit Selected !");
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.KEY_APP_PREFERENCES, MODE_PRIVATE);
                boolean commit =sharedPreferences.edit().remove(LoginActivity.KEY_LOGIN).commit();
                Log.d(TAG_LOG, "REMOVE SHARED PREFERENCES KEY LOGIN --> " + commit);
                startActivity(new Intent(this, LoginActivity.class));
                MainActivity.this.finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
