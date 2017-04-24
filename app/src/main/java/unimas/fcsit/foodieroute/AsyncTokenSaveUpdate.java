package unimas.fcsit.foodieroute;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.UUID;

/**
 * Created by elliotching on 11-Apr-17.
 */

public class AsyncTokenSaveUpdate {

    Context context;
    AppCompatActivity activity;
    ResFR r;

    AsyncTokenSaveUpdate(final Context context) {
        this.context = context;
        this.activity = (AppCompatActivity) context;
        r = new ResFR(context);
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);

        final Dialog_Progress pd = new Dialog_Progress(activity,
                r.string(R.string.s_prgdialog_title_noti_service),
                r.string(R.string.s_prgdialog_update_token), false);
        final String token = FirebaseInstanceId.getInstance().getToken();
        if (token == null) {
            System.out.println("token = " + token);
            pd.dismiss();

            new Dialog_AlertNotice(context,
                    r.string(R.string.s_dialog_title_fail),
                    r.string(R.string.s_notif_msg_notif_token_unavailable))
                    .setPositiveKey(
                            r.string(R.string.s_dialog_btn_ok), null);
            new PushNotification(context).createNotification(r.string(R.string.s_notif_msg_notif_token_unavailable));
        } else {
            String deviceModel = Build.MODEL;
            String deviceUUID = pref.getString("FR_deviceUUID", "empty");
            final String username = pref.getString("FR_username", "empty");
            pref.edit().putString("FR_token", token).apply();

            if (deviceUUID.equals("empty")) {
                deviceUUID = UUID.randomUUID().toString();
                pref.edit().putString("FR_deviceUUID", deviceUUID).apply();
            }

            RequestParams params = new RequestParams();
            params.put("pass", "!@#$");
            params.put("table", "tokens_testing");
            params.put("token", token);
            params.put("device_key", deviceModel + "-" + deviceUUID);
            params.put("username", username);
            params.put("testing", 0);

            new AsyncHTTPPost(ResFR.URL_insert_token, params, false, new InterfaceAsyncHTTPPost() {
                @Override
                public void notifyHTTPSuccess(int code, String result) {
                    pd.dismiss();
                    new Dialog_AlertNotice(context, "Success", result).setPositiveKey("OK", null);
                    new PushNotification(context).createNotification(ResFR.string(context, R.string.s_notif_msg_notif_token_log_in_update)+username);
                }

                @Override
                public void notifyHTTPFailure(int code, String result) {
                    pd.dismiss();
                    new Dialog_AlertNotice(context, "Error " + code, result).setPositiveKey("OK", null);
                }

                @Override
                public void notifyJSONArraySuccess(JSONArray jsonarray) {

                }
            });
        }

    }
}
