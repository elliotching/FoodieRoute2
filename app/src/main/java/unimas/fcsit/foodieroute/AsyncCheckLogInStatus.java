package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by elliotching on 11-Apr-17.
 */

public class AsyncCheckLogInStatus {

    Context context;
    AppCompatActivity activity;
    ResFR r;

    AsyncCheckLogInStatus(Context context) {
        this.context = context;
        this.activity = (AppCompatActivity) context;
        r = new ResFR(context);

        final Dialog_Progress pd = new Dialog_Progress(activity,
                r.string(R.string.s_prgdialog_title_noti_service),
                r.string(R.string.s_prgdialog_update_token), false);

        final String token = FirebaseInstanceId.getInstance().getToken();

        if (token == null) {

            pd.dismiss();

            new Dialog_AlertNotice(context,
                    r.string(R.string.s_dialog_title_fail),
                    r.string(R.string.s_notif_msg_token_unavailable))
                    .setPositiveKey(
                            r.string(R.string.s_dialog_btn_ok), null);

            new Dialog_AlertNotice(context, R.string.s_dialog_title_warning, R.string.s_notif_msg_token_unavailable)
                    .setPositiveKey(R.string.s_dialog_btn_ok, null);

        } else {

            String deviceUUID = ResFR.getPrefString(context, ResFR.FR_DEVICEUUID);
            String username = ResFR.getPrefString(context, ResFR.FR_USERNAME);
            String device = ResFR.getPrefString(context, ResFR.FR_DEVICE);
            String tokenp = ResFR.getPrefString(context, ResFR.FR_TOKEN);
            if(tokenp.equals(token)){
                Log.e(this.getClass().getSimpleName(), "token from sahredPreference matched");
            }else{
                Log.e(this.getClass().getSimpleName(), "token from sahredPreference DO NOT matched");
            }

            ResFR.setPrefString(context, ResFR.FR_TOKEN, token);

            Log.e(this.getClass().getSimpleName(), "deviceUUID = "+deviceUUID);
            Log.e(this.getClass().getSimpleName(), "device = "+device);
            Log.e(this.getClass().getSimpleName(), "username = "+username);
            Log.e(this.getClass().getSimpleName(), "token = "+tokenp);

            if(username.equals(ResFR.DEFAULT_EMPTY)){
                pd.dismiss();

                new Dialog_AlertNotice(context, R.string.s_dialog_title_warning, R.string.s_dialog_msg_not_logged_in)
                        .setPositiveKey(R.string.s_dialog_btn_ok, null);

            }else{
                String[][] data = new String[][]{
                        {"pass", "!@#$"},
                        {"token", token},
                        {"deviceUUID", deviceUUID},
                        {"device", device},
                        {"username", username}
                };

                new HTTP_POST(context, data, ResFR.URL_check_log_in_status, pd);
            }

        }
    }

    private class HTTP_POST implements InterfaceCustomHTTP{
        Dialog_Progress progress;
        HTTP_POST(Context c, String[][] d, String url, Dialog_Progress p){
            progress = p;
            CustomHTTP cc = new CustomHTTP(c, d, url);
            cc.ui = this;
            cc.execute();
        }
        @Override
        public void onCompleted(String result) {
            progress.dismiss();

            if(result.matches("")){
                Log.e(this.getClass().getSimpleName(), "result empty");
            }else{
                try {
                    JSONObject json = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();

                    String WarningTitle = ResFR.string(context, R.string.s_dialog_title_warning);
                    new Dialog_AlertNotice(context, WarningTitle, result)
                            .setPositiveKey(R.string.s_dialog_btn_ok, null);
                }
            }
            // no respond. wait for notification to log you out if different token found.
        }
    }
}
