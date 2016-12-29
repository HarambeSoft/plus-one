package harambesoft.com.plusone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import harambesoft.com.plusone.fragments.PollFragment;
import harambesoft.com.plusone.fragments.SettingsFragment;
import harambesoft.com.plusone.fragments.SignInFragment;
import harambesoft.com.plusone.services.LocationTrackerService;
import harambesoft.com.plusone.Constants.*;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LOCATION_PERMISSION_REQUEST = 1;

    private NavigationView navigationView = null;
    private TextView textViewUserNameNavHeader = null;
    private TextView textViewEmailNavHeader = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        loadDrawer();
        loadActionButton();
        assignWidgets();
        checkUserLogin();
        kindlyAskForLocationPermissions();

        // Check intent data
        Intent intent = getIntent();
        if (intent.hasExtra(NotificationData.POLL_ID)) {
            // We have a poll to show
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PollFragment.newInstance(intent.getIntExtra(NotificationData.POLL_ID, 0)))
                    .commit();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            for (int i = 0; i < permissions.length; i++) {
                // String permission = permissions[i];
                // No need to check what are the permissions, because we only have one request for now

                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    // We have permission now, so we can start service
                    startLocationTrackerService();
                }
            }
        }
    }

    private void startLocationTrackerService() {
        startService(new Intent(this, LocationTrackerService.class));
        Log.d("PLUSONE/SERVICE", "LocationTrackerService started.");
    }

    private void kindlyAskForLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST);
        } else {
            // We already have our permissions, so just start the service
            startLocationTrackerService();
        }

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

        // TODO: replace with Butterknife calls
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
