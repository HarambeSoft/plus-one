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

    public interface LoginFinishedHandler {
        public void onLoginFinished(boolean success);
    }

    private static void sendPOSTRequest(String path, String[] pathArgs, POSTData postData, Request.RequestFinishedHandler handler) throws IOException {
        String url = PlusOneAPI.URL + String.format(path, (Object[]) pathArgs);
        Request.post(url, postData, handler);
    }

    public static void login(final String name, String password, final LoginFinishedHandler handler) throws IOException {
        POSTData postData = new POSTData();
        postData.put("name", name);
        postData.put("password", password);

        PlusOneAPI.sendPOSTRequest("token", new String[]{}, postData, new Request.RequestFinishedHandler() {
            @Override
            public void onRequestFinished(String result) {
                try {
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

                        handler.onLoginFinished(true);
                    } else {
                        handler.onLoginFinished(false);
                    }
            } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static boolean signUp(String name, String email, String password) throws IOException {
        POSTData postData = new POSTData();
        postData.put("name", name);
        postData.put("email", email);
        postData.put("password", password);

        //String result = PlusOneAPI.sendPOSTRequest("user", new String[] {}, postData);
        //TODO: parse result, then login if user has created
        return true;
    }

}
