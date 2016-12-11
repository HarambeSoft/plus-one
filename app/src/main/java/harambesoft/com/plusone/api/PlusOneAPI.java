package harambesoft.com.plusone.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by isa on 11.12.2016.
 */

public class PlusOneAPI {
    public static final String URL = "http://192.168.1.192/~isa/plus-one-server/public/index.php/api/v1/";


    public static String sendPOSTRequest(String path, String[] pathArgs, POSTData postData) throws IOException {
        String url = PlusOneAPI.URL + String.format(path, (Object[]) pathArgs);
        return Request.post(url, postData);
    }

    public static boolean signUp(String userName, String email, String password) throws IOException {
        POSTData postData = new POSTData();
        postData.put("name", userName);
        postData.put("email", email);
        postData.put("password", password);

        Log.d("USER REGISTER:", PlusOneAPI.sendPOSTRequest("user", new String[] {}, postData));
        return true;
    }

}
