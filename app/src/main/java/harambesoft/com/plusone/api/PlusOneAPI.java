package harambesoft.com.plusone.api;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.PlusOne;

/**
 * Created by isa on 11.12.2016.
 */

public class PlusOneAPI {

    private static final String URL = "http://plusone.isamert.net/public/api/v1/";
    //FIXME: change at production

    public interface LoginFinishedHandler {
        public void onLoginFinished(boolean success, String message);
    }

    public interface SignupFinishedHandler {
        public void onSignupFinished(boolean success, String message);
    }

    private static String[] encodeParams(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            try {
                strings[i] = URLEncoder.encode(strings[i], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return strings;
    }

    private static void sendPOSTRequest(String path, String[] pathArgs, POSTData postData, Request.RequestFinishedHandler handler) throws IOException {
        String url = PlusOneAPI.URL + String.format(path, ((Object[]) encodeParams(pathArgs)));
        Request.post(url, postData, handler);
    }

    private static void sendGETRequest(String path, String[] pathArgs, Request.RequestFinishedHandler handler) throws IOException {
        String url = PlusOneAPI.URL + String.format(path, ((Object[]) encodeParams(pathArgs)));
        Request.get(url, handler);
    }

    public static void login(final String name, String password, final LoginFinishedHandler handler) throws IOException {
        final POSTData postData = new POSTData();
        postData.put("name", name);
        postData.put("password"
                , password);

        PlusOneAPI.sendPOSTRequest("token", new String[]{}, postData, new Request.RequestFinishedHandler() {
            @Override
            public void onRequestFinished(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    boolean error = resultJson.getBoolean("error");
                    Log.d("LOGIN RESPONSE", result);

                    if (!error) {
                        JSONObject userJson = resultJson.getJSONObject("user");

                        // Add token to SharedPreferences for later use
                        SharedPreferences.Editor editor = PlusOne.settings().edit();
                        editor.putString("api_token", resultJson.getString("api_token"));
                        editor.putString("name", name);
                        editor.putString("email", userJson.getString("email"));
                        editor.putString("id", userJson.getString("id"));


                        CurrentUser.login(userJson.getString("id"),
                                            userJson.getString("name"),
                                            userJson.getString("email"),
                                            resultJson.getString("api_token"));
                        handler.onLoginFinished(true, "");
                    } else {
                        handler.onLoginFinished(false, resultJson.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static boolean signUp(final String name, String email, String password, final SignupFinishedHandler handler) throws IOException {
        POSTData postData = new POSTData();
        postData.put("name", name);
        postData.put("email", email);
        postData.put("password", password);

        PlusOneAPI.sendPOSTRequest("user", new String[]{}, postData, new Request.RequestFinishedHandler() {
            @Override
            public void onRequestFinished(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    boolean error = resultJson.getBoolean("error");
                    Log.d("USER REGISTER RESULT", result);

                    if (!error) {
                        handler.onSignupFinished(true, "");
                    } else {
                        handler.onSignupFinished(false, resultJson.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    public static void user(int id) throws IOException {
        PlusOneAPI.sendGETRequest("user/%s", new String[] {Integer.toString(id)}, new Request.RequestFinishedHandler() {
            @Override
            public void onRequestFinished(String result) {
                try {
                    JSONObject jsonUser = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void user() throws IOException {
        PlusOneAPI.sendGETRequest("user", new String[] {}, new Request.RequestFinishedHandler() {
            @Override
            public void onRequestFinished(String result) {
                try {
                    JSONArray jsonUsers = new JSONArray(result);
                    for (int i = 0; i < jsonUsers.length(); i++) {
                        JSONObject jsonUser = jsonUsers.getJSONObject(i);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
