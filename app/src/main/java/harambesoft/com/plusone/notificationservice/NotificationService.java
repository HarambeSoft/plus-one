package harambesoft.com.plusone.notificationservice;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by isa on 10.12.2016.
 */

public class NotificationService extends IntentService {
    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}