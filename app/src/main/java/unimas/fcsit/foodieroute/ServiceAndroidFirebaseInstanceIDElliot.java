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

/**
 * Created by elliotching on 02-Mar-17.
 */

public class ServiceAndroidFirebaseInstanceIDElliot extends FirebaseInstanceIdService {

    private static final String TAG = "MyAndroidFCMIIDService";
    Context context = this;
    static SharedPreferences pref;
    static String deviceUUID;
    static String device;
    static String username;
    static String token;
    static String firebase_IID;

    @Override
    public void onTokenRefresh() {
        pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);

        getAllPref();

        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Log the token
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        if(!token.equals(refreshedToken) && refreshedToken != null){
            token = refreshedToken;
            pref.edit().putString("FR_token",refreshedToken).commit();
        }

        if(refreshedToken == null){
            new PushNotification(context).createNotification("Token is not available on this device.");
        }else{
            saveTokenToServer(refreshedToken);
        }
    }

    private void saveTokenToServer(String token) {

        if (deviceUUID.equals("empty")) {
            SharedPreferences.Editor edit = pref.edit();

            String deviceModel = Build.MODEL;
            deviceUUID = deviceModel + "-" + UUID.randomUUID().toString();
            device = deviceModel;

            edit.putString("FR_deviceUUID", deviceUUID);
            edit.putString("FR_device", device);
            edit.commit();
        }

        String[][] data = {
                {"pass", "!@#$"},
                {"token", token},
                {"deviceUUID", deviceUUID},
                {"device", device},
                {"username", username}
        };

        if(!username.equals("empty")) { // username values
            new HTTP_POST(context, data, ResFR.URL_on_token_refresh);
        }
    }

    public void getAllPref() {
        deviceUUID = pref.getString("FR_deviceUUID", "empty");
        device = pref.getString("FR_device", "empty");
        username = pref.getString("FR_username", "empty");
        token = pref.getString("FR_token", "empty");
        firebase_IID = pref.getString("FR_firebase_IID", "empty");
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