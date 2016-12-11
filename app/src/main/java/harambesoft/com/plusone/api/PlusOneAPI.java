package harambesoft.com.plusone.api;

import org.json.JSONObject;

/**
 * Created by isa on 11.12.2016.
 */

public class PlusOneAPI {
    public static final String URL = "localhost:8000/api/v1/";



    public static String sendRequest(String path, String[] pathArgs, JSONObject postJson) {
        String url = PlusOneAPI.URL + String.format(path, (Object[]) pathArgs);
        return "";
    }

    public static boolean signUp(String userName, String email, String password) {
        //PlusOneAPI.sendRequest("user", "", new JSONObject(), new StringRequest());
        return true;
    }

}
