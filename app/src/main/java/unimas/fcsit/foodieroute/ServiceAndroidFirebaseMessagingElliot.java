package unimas.fcsit.foodieroute;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by elliotching on 02-Mar-17.
 */

public class ServiceAndroidFirebaseMessagingElliot extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String,String> data = remoteMessage.getData();
        Log.d(TAG, "Notification Message Body: " + data.get("body"));
        String body  = data.get("body");
        String time = null;
        if(data.containsKey("time")) {
            time = data.get("time");
        }

        //create notification
        new PushNotification(context).createNotification(body);
    }

    Context context = this;






}
