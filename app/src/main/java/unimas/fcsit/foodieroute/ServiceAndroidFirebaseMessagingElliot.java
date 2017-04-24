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
    Context context = this;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String,String> data = remoteMessage.getData();
        Log.d(TAG, "Notification Message Body: " + data.get("body"));
        String body  = data.get("body");
        String log_out  = data.get("log_out");
        String time = "";
        if(data.containsKey("time")) {
            time = data.get("time");
        }

        if(log_out.equals("0")) {
            //create notification
            new PushNotification(context).createNotification(body);
        }
        if(log_out.equals("1")) {

            if (Helper.isAppRunning(context, "unimas.fcsit.foodieroute")) {
                // App is running
                new Dialog_AlertNotice(context,
                        R.string.s_dialog_title_warning,
                        R.string.s_notif_msg_been_logged_out)
                        .setPositiveKey(R.string.s_dialog_btn_ok, null);
            } else {
                // App is not running
                // create notification
                String msg = ResFR.string(context, R.string.s_notif_msg_been_logged_out);
                new PushNotification(context).createNotification(msg);
            }
        }
    }

}
