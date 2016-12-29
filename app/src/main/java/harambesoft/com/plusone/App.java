package harambesoft.com.plusone;

import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import harambesoft.com.plusone.fragments.PollFragment;

/**
 * Created by isa on 28.12.2016.
 */

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static SharedPreferences settings() {
        return PreferenceManager.getDefaultSharedPreferences(App.context);
    }
}