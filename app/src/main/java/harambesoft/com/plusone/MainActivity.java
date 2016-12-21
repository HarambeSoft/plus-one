package harambesoft.com.plusone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import harambesoft.com.plusone.fragments.ActivityStreamFragment;
import harambesoft.com.plusone.fragments.CategoriesFragment;
import harambesoft.com.plusone.fragments.DiscoverFragment;
import harambesoft.com.plusone.fragments.MeFragment;
import harambesoft.com.plusone.fragments.NewPollFragment;
import harambesoft.com.plusone.fragments.PollsFragment;
import harambesoft.com.plusone.fragments.SettingsFragment;
import harambesoft.com.plusone.fragments.SignInFragment;
import harambesoft.com.plusone.services.LocationTrackerService;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static Context appContext;

    private NavigationView navigationView = null;
    private TextView textViewUserNameNavHeader = null;
    private TextView textViewEmailNavHeader = null;

    private static final String TAG = MainActivity.class.getSimpleName();

    // TODO - insert your themoviedb.org API KEY here

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set appContext
        appContext = this.getApplicationContext();

        setContentView(R.layout.activity_main);

        loadDrawer();
        loadActionButton();

        assignWidgets();
        checkUserLogin();

        // Start location tracker
        startService(new Intent(this, LocationTrackerService.class));

    }

    private void loadDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new NewPollFragment())
                        .commit();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
    }

    private void assignWidgets() {
        // WORKAROUND: Need to inflate navigation header programmatically, otherwise we get a nullpointer
        // This does not work:
        // app:headerLayout="@layout/nav_header_main"
        // in activity_main -> android.support.design.widget.NavigationView
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);

        textViewEmailNavHeader = (TextView) header.findViewById(R.id.textViewEmailNavHeader);
        textViewUserNameNavHeader = (TextView) header.findViewById(R.id.textViewUsernameNavHeader);
    }

    public boolean checkUserLogin() {
        if (CurrentUser.exists()) {
            textViewUserNameNavHeader.setText(CurrentUser.name());
            textViewEmailNavHeader.setText(CurrentUser.email());

            // User is logged in, redirect to ActivityStream
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ActivityStreamFragment())
                    .commit();

            return true;
        } else {
            // Redirect to login screen
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new SignInFragment())
                    .commit();
            return false;
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        //TODO: Add maps view
        FragmentManager fragmentManager = getSupportFragmentManager();
        /* PApplet sketch = new Sketch();
        PFragment fragment = new PFragment();
        fragment.setSketch(sketch); */

        //TODO: Fix fragments

        int id = item.getItemId();

        if (id == R.id.nav_activity_stream) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ActivityStreamFragment.newInstance())
                    .commit();
        } else if (id == R.id.nav_polls) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new PollsFragment())
                    .commit();
        } else if (id == R.id.nav_categories) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new CategoriesFragment())
                    .commit();
        } else if (id == R.id.nav_discover) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new DiscoverFragment())
                    .commit();
        } else if (id == R.id.nav_me) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new MeFragment())
                    .commit();
        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new SettingsFragment())
                    .commit();
        } else if (id == R.id.nav_logout) {
            // Logout
            CurrentUser.logout();
            this.checkUserLogin();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

