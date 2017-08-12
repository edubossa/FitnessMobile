package com.ews.fitnessmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ews.fitnessmobile.fragments.AboutFragment;
import com.ews.fitnessmobile.fragments.StudentFragment;
import com.ews.fitnessmobile.fragments.UnitsAddFragment;
import com.ews.fitnessmobile.fragments.UnitsFragment;
import com.ews.fitnessmobile.fragments.UnitsMapActivity;
import com.ews.fitnessmobile.model.Login;
import com.ews.fitnessmobile.model.MenuNavigationView;
import com.ews.fitnessmobile.model.Role;
import com.facebook.login.LoginManager;

import static com.ews.fitnessmobile.model.MenuNavigationView.UNITS;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_LOG = "MainActivity";

    private Login login;

    public static FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAdd.setVisibility(FloatingActionButton.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new UnitsAddFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (getIntent() != null) {
            this.login = getIntent().getParcelableExtra(LoginActivity.PUT_LOGIN);
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            if (this.login.getRole().equals(Role.ADMIN)) {
                tx.replace(R.id.content_main, new UnitsFragment());
            } else {
                tx.replace(R.id.content_main, new StudentFragment());
            }
            tx.addToBackStack(null);
            tx.commit();
        }

        addMenuItemInNavMenuDrawer();
    }


    private void addMenuItemInNavMenuDrawer() {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        if (this.login.getRole().equals(Role.ADMIN)) {
            menu.add(1, UNITS.getItemId(), 1, getResources().getString(R.string.menu_units));
            menu.add(1, MenuNavigationView.MAP.getItemId(), 2, getResources().getString(R.string.menu_show_map));
        } else {
            menu.add(1, MenuNavigationView.TRAINING.getItemId(), 3, getResources().getString(R.string.menu_training));
            menu.add(1, MenuNavigationView.ACCOUNT.getItemId(), 4, getResources().getString(R.string.menu_account));
        }
        menu.add(1, MenuNavigationView.ABOUT.getItemId(), 5, getResources().getString(R.string.menu_about));
        menu.add(1, MenuNavigationView.EXIT.getItemId(), 6, getResources().getString(R.string.menu_exit));

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
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.login.getRole().equals(Role.ADMIN)) {
            //getMenuInflater().inflate(R.menu.main, menu);
            fabAdd.setVisibility(FloatingActionButton.INVISIBLE);
        }
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
        // Handle navigation view item clicks here.
        Log.d(TAG_LOG, "onNavigationItemSelected ID -->  " + item.getItemId());
        MenuNavigationView menu = MenuNavigationView.findMenu(item.getItemId());
        switch (menu) {
            case UNITS:
                Log.d(TAG_LOG, "Menu Units Selected !");
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new UnitsFragment())
                    .addToBackStack(null)
                    .commit();
                break;
            case MAP:
                Log.d(TAG_LOG, "Menu Student Selected !");
                /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new StudentFragment())
                    .addToBackStack(null)
                    .commit();*/

                startActivity(new Intent(this, UnitsMapActivity.class));


                break;
            case TRAINING:
                Log.d(TAG_LOG, "Menu Treino Selected !");
                break;
            case ACCOUNT:
                Log.d(TAG_LOG, "Menu Conta Selected !");
                break;
            case ABOUT:
                Log.d(TAG_LOG, "Menu About Selected !");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new AboutFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case EXIT:
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
