package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Map data = remoteMessage.getData();
        Log.e(TAG, "Notification Message Body: " + data.get("body"));
        String body  = (String) data.get("body");
        String log_out  = (String) data.get("log_out");

        Log.e(this.getClass().getSimpleName(), data.get("log_out").toString());

        String time = "";
        if(data.containsKey("time")) {
            time = (String) data.get("time");
        }

        if(log_out.equals("0")) {

            //create notification
            new PushNotification(context).createNotification(body);
        }
        if(log_out.equals("1")) {

            Log.e(this.getClass().getSimpleName(), "YOU HAVE BEEN FORCED LOG OUT!!!");
            // USER HAS BEEN FORCED LOG OUT!!!
            ResFR.setPrefString(context, ResFR.FR_USERNAME, ResFR.DEFAULT_EMPTY);

            if (Helper.isAppRunning(context, "unimas.fcsit.foodieroute")) {
                // App is running

                Bundle bundle = new Bundle();
                bundle.putBoolean(ResFR.BUNDLE_KEY_KICKED_OUT, true);

                Intent ii = new Intent(context, ActivityLogIn.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                ii.putExtras(bundle);

                startActivity(ii);
            } else {
                // App is not running
                // create notification
                String msg = ResFR.string(context, R.string.s_notif_msg_been_logged_out);
                new PushNotification(context).createNotification(msg);
            }
        }
    }

}
