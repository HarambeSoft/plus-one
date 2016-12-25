package harambesoft.com.plusone;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import harambesoft.com.plusone.services.LocationTrackerService;

/**
 * Created by isa on 12.12.2016.
 */

public class PlusOne {
    private static SharedPreferences mSettings = null;

    public static SharedPreferences settings() {
        if (mSettings == null) {
            Context appContext = MainActivity.getAppContext();
            Context serviceContext = LocationTrackerService.getContext();

            // if appContext is null, then call is coming from service
            Context currentContext = appContext == null ? serviceContext:appContext;


            mSettings = PreferenceManager.getDefaultSharedPreferences(currentContext);
        }
        return mSettings;
    }
}
