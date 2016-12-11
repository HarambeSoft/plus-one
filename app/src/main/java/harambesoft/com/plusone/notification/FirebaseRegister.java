package harambesoft.com.plusone.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by isa on 10.12.2016.
 */

public class FirebaseRegister extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseRegister";

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String token = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "FCM Refreshed token: " + token);

        //sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
    }
}