package harambesoft.com.plusone.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import harambesoft.com.plusone.Constants.*;
import harambesoft.com.plusone.MainActivity;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.helpers.ActivityStream;

/**
 * Created by isa on 10.12.2016.
 */

public class FirebaseMessageReciever extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessageReciever";
    private Map<String, String> lastData = new HashMap<>();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        lastData.putAll(data);


        String title = "PlusOne Notification";
        String body = "No info about this notification.";
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
        }

        ActivityStream.add(title, body);
        //TODO: add an event for activity(what happens when we click that activity?)
        //FIXME: cant save notification if app is not running on foreground
        // maybe service and activity does not use same preferences

        sendNotification(title, body);
    }

    // This method is only generating push notification
    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (lastData.containsKey(NotificationData.POLL_ID))
            intent.putExtra(NotificationData.POLL_ID, Integer.valueOf(lastData.get(NotificationData.POLL_ID)));


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}