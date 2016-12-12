package harambesoft.com.plusone;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by isa on 12.12.2016.
 */

public class PlusOne {
    private static SharedPreferences mSettings = null;

    public static SharedPreferences settings() {
        if (mSettings == null)
            mSettings = PreferenceManager.getDefaultSharedPreferences(MainActivity.getAppContext());
        return mSettings;
    }

    public static String firebaseUserTopic() {
        // if user is not logged in, we choose NotLoggedIn as topic
        // so we can send notifications to users that are not logged in
        return "user_" + PlusOne.settings().getString("name", "NotLoggedIn");
    }
}
