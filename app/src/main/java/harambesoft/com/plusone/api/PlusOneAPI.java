package harambesoft.com.plusone.api;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import harambesoft.com.plusone.MainActivity;
import harambesoft.com.plusone.PlusOne;

/**
 * Created by isa on 11.12.2016.
 */

public class PlusOneAPI {
    private static final String URL = "http://192.168.1.28/~isa/plus-one-server/public/index.php/api/v1/";
    //FIXME: change at production


    private static String sendPOSTRequest(String path, String[] pathArgs, POSTData postData) throws IOException {
        String url = PlusOneAPI.URL + String.format(path, (Object[]) pathArgs);
        return Request.post(url, postData);
    }

    public static boolean login(String name, String password) throws IOException, JSONException {
        POSTData postData = new POSTData();
        postData.put("name", name);
        postData.put("password", password);

        String result = PlusOneAPI.sendPOSTRequest("token", new String[] {}, postData);
        JSONObject resultJson = new JSONObject(result);
        JSONObject userJson = resultJson.getJSONObject("user");

        boolean error = resultJson.getBoolean("error");
        if (!error) {
            // Add token to SharedPreferences for later use
            SharedPreferences.Editor editor = PlusOne.settings().edit();
            editor.putString("api_token", resultJson.getString("api_token"));
            editor.putString("name", name);
            editor.putString("email", userJson.getString("email"));
            editor.putString("id", userJson.getString("id"));
            editor.apply();
            return true;
        }

        return false;
    }

    public static boolean signUp(String name, String email, String password) throws IOException {
        POSTData postData = new POSTData();
        postData.put("name", name);
        postData.put("email", email);
        postData.put("password", password);

        String result = PlusOneAPI.sendPOSTRequest("user", new String[] {}, postData);
        //TODO: parse result, then login if user has created
        return true;
    }

}
