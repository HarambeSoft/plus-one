package harambesoft.com.plusone;

import android.content.SharedPreferences;
import android.location.Location;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by isa on 14.12.2016.
 */

public class CurrentUser {
    public static boolean exists() {
        return !CurrentUser.id().isEmpty();
    }

    public static void login(String id, String name, String email, String apiToken) {
        SharedPreferences.Editor editor = PlusOne.settings().edit();
        editor.putString("id", id);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("api_token", apiToken);
        editor.commit();

        // Subscribe to user notification channel
        FirebaseMessaging.getInstance().subscribeToTopic(CurrentUser.notificationTopic());
    }

    public static void logout() {
        PlusOne.settings().edit().clear().commit();
        FirebaseMessaging.getInstance().unsubscribeFromTopic(CurrentUser.notificationTopic());
    }

    public static void updateLocation(Location location, String time) {
        if (CurrentUser.exists()) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference locationsRef = database.getReference("locations");

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("lat", location.getLatitude());
            userMap.put("long", location.getLongitude());
            userMap.put("last_update", time);
            locationsRef.child(CurrentUser.id()).setValue(userMap);
        }
    }

    public static String id() {
        return PlusOne.settings().getString("id", "");
    }

    public static String name() {
        return PlusOne.settings().getString("name", "");
    }

    public static String email() {
        return PlusOne.settings().getString("email", "");
    }

    public static String apiToken() {
        return PlusOne.settings().getString("api_token", "");
    }

    public static String notificationTopic() {
        // if user is not logged in, we choose NotLoggedIn as topic
        // so we can send notifications to users that are not logged in
        return "user_" + PlusOne.settings().getString("name", "NotLoggedIn");
    }

}
