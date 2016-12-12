package harambesoft.com.plusone;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import harambesoft.com.plusone.fragments.ActivityStreamFragment;
import harambesoft.com.plusone.fragments.SignInFragment;
import harambesoft.com.plusone.fragments.SignUpFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static Context appContext;

    private NavigationView navigationView = null;
    private TextView textViewUserNameNavHeader = null;
    private TextView textViewEmailNavHeader = null;

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = this.getApplicationContext();

        setContentView(R.layout.activity_main);

        loadDrawer();
        loadActionButton();

        assignWidgets();
        checkUserLogin();
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        // Check if user logged in
        String userName = PlusOne.settings().getString("name", "");
        String email = PlusOne.settings().getString("email", "");

        if (!userName.isEmpty()) {
            // Subscribe to user notification channel
            FirebaseMessaging.getInstance().subscribeToTopic("user_" + userName);
            Log.d("MainActivity", "User is logged in.");
            Log.d("MainActivity", "Subbed to notifications of " + userName);

            textViewUserNameNavHeader.setText(userName);
            textViewEmailNavHeader.setText(email);

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
        SignInFragment fragmentSignIn = new SignInFragment();
        SignUpFragment fragmentSignUp = new SignUpFragment();

        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            /*fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();*/
        } else if (id == R.id.nav_gallery) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragmentSignIn)
                    .commit();
        } else if (id == R.id.nav_slideshow) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragmentSignUp)
                    .commit();
        } else if (id == R.id.nav_manage) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new ActivityStreamFragment())
                    .commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
