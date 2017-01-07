package harambesoft.com.plusone;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.FrameLayout;
import android.widget.TextView;

import harambesoft.com.plusone.Constants.NotificationData;
import harambesoft.com.plusone.fragments.ActivityStreamFragment;
import harambesoft.com.plusone.fragments.CategoriesFragment;
import harambesoft.com.plusone.fragments.MeFragment;
import harambesoft.com.plusone.fragments.NewPollFragment;
import harambesoft.com.plusone.fragments.SettingsFragment;
import harambesoft.com.plusone.fragments.SignInFragment;
import harambesoft.com.plusone.services.LocationTrackerService;
import processing.android.PFragment;
import processing.core.PApplet;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LOCATION_PERMISSION_REQUEST = 1;

    private NavigationView navigationView = null;
    private TextView textViewUserNameNavHeader = null;
    private TextView textViewEmailNavHeader = null;

    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Update main activity
        App.setMainActivity(this);

        loadDrawer();
        loadActionButton();
        assignWidgets();
        checkUserLogin();
        kindlyAskForLocationPermissions();

        // Check intent data
        Intent intent = getIntent();
        if (intent.hasExtra(NotificationData.POLL_ID)) {
            // We have a poll to show
            int pollID = intent.getIntExtra(Constants.NotificationData.POLL_ID, 0);
            App.showPoll(pollID);
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
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new NewPollFragment(), "NewPollTag")
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
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new ActivityStreamFragment(), "ActivityStreamTag")
                    .commit();

            return true;
        } else {
            // Redirect to login screen
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new SignInFragment(), "SignInTag")
                    .commit();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {

            FragmentManager fragmentManager = getFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);

            if (currentFragment.getTag().equals("ActivityStreamTag"))
            {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 500);
            }
            else if(currentFragment.getTag().equals("SignUpTag")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new SignInFragment(), "SignInTag");
                fragmentTransaction.commit();
            }
            else if(currentFragment.getTag().equals("SignInTag")) {
                super.onBackPressed();
            }
            else
            {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new ActivityStreamFragment(), "ActivityStreamTag");
                fragmentTransaction.commit();
            }

        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        //TODO: Add maps view
        //TODO: Fix fragments

        int id = item.getItemId();

        if (id == R.id.nav_activity_stream) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new ActivityStreamFragment(), "ActivityStreamTag")
                    .commit();
        } else if (id == R.id.nav_categories) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new CategoriesFragment(), "CategoriesTag")
                    .commit();
        } else if (id == R.id.nav_discover) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            PApplet sketch = new Sketch();
            PFragment fragment = new PFragment();
            fragment.setSketch(sketch);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, "DiscoverTag")
                    .commit();
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container);
            Sketch.viewHeight= frameLayout.getHeight();
            Sketch.viewWidth = frameLayout.getWidth();
        } else if (id == R.id.nav_me) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new MeFragment(), "MeTag")
                    .commit();
        } else if (id == R.id.nav_settings) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new SettingsFragment(), "SettingsTag")
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
