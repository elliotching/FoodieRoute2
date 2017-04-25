package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import static unimas.fcsit.foodieroute.ResFR.DEFAULT_EMPTY;
import static unimas.fcsit.foodieroute.ResFR.FR_DEVICE;
import static unimas.fcsit.foodieroute.ResFR.FR_DEVICEUUID;
import static unimas.fcsit.foodieroute.ResFR.FR_FIREBASE_INSTANCE_ID;
import static unimas.fcsit.foodieroute.ResFR.FR_TOKEN;
import static unimas.fcsit.foodieroute.ResFR.FR_USERNAME;

/**
 * Created by elliotching on 02-Mar-17.
 */

public class ServiceAndroidFirebaseInstanceIDElliot extends FirebaseInstanceIdService {

    private final String TAG = this.getClass().getSimpleName();
    Context context = this;
    static SharedPreferences pref;
    static String deviceUUID;
    static String device;
    static String username;
    static String firebase_IID;

    @Override
    public void onTokenRefresh() {

        getAllPref();

        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        ResFR.setPrefString(context, ResFR.FR_TOKEN, refreshedToken);

        //Log the token
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        saveTokenToServer(refreshedToken);

    }

    private void saveTokenToServer(String token) {

        String notification = ResFR.string(context, R.string.s_notif_msg_token_updated);

        String[][] data = {
                {"pass", "!@#$"},
                {"token", token},
                {"deviceUUID", deviceUUID},
                {"device", device},
                {"username", username},
                {"notification_data_body", notification}
        };

        if(username.equals(DEFAULT_EMPTY) || deviceUUID.equals(DEFAULT_EMPTY) || device.equals(DEFAULT_EMPTY)) {

        }else{
            new HTTP_POST(context, data, ResFR.URL_on_token_refresh);
        }
    }

    public void getAllPref() {
        pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        deviceUUID = pref.getString( FR_DEVICEUUID, DEFAULT_EMPTY);
        device = pref.getString( FR_DEVICE, DEFAULT_EMPTY);
        username = pref.getString( FR_USERNAME, DEFAULT_EMPTY);
        firebase_IID = pref.getString( FR_FIREBASE_INSTANCE_ID, DEFAULT_EMPTY);
    }

    class HTTP_POST implements InterfaceCustomHTTP{
        HTTP_POST(Context c, String[][] d, String url){
            CustomHTTP cc = new CustomHTTP(c, d, url);
            cc.ui = this;
            cc.execute();
        }
        @Override
        public void onCompleted(String result) {

        }
    }
}