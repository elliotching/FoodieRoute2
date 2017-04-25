package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.UUID;

import static unimas.fcsit.foodieroute.ResFR.DEFAULT_EMPTY;
import static unimas.fcsit.foodieroute.ResFR.FR_DEVICE;
import static unimas.fcsit.foodieroute.ResFR.FR_DEVICEUUID;
import static unimas.fcsit.foodieroute.ResFR.FR_FIREBASE_INSTANCE_ID;
import static unimas.fcsit.foodieroute.ResFR.FR_USERNAME;

/**
 * Created by elliotching on 28-Jun-16.
 */
public class SplashScreen extends AppCompatActivity {

    Context context = this;
    TimerTask_FR timerTask;
    boolean tokenSuccess = false;

    public void onCreate(Bundle ins) {
        super.onCreate(ins);

        String token = ResFR.getPrefString(context, ResFR.FR_TOKEN);
        final String deviceUUID = ResFR.getPrefString(context, ResFR.FR_DEVICEUUID);
        final String device = ResFR.getPrefString(context, ResFR.FR_DEVICE);

        timerTask = new TimerTask_FR(10, new InterfaceMyTimerTask() {
            @Override
            public void doTask() {
                getFirebaseToken(deviceUUID, device);
            }

            @Override
            public void secondsPassed(int runningTime) {

            }
        });

    }

    private void getFirebaseToken( String deviceUUID, String device) {

        if(deviceUUID.equals(ResFR.DEFAULT_EMPTY) || device.equals(ResFR.DEFAULT_EMPTY)) {

            Log.e(this.getClass().getSimpleName(), "deviceUUID and device NOT FOUND!!!");

            String deviceModel = Build.MODEL;
            deviceUUID = deviceModel + "-" + UUID.randomUUID().toString();
            device = deviceModel;

            ResFR.setPrefString(context, FR_DEVICEUUID, deviceUUID);
            ResFR.setPrefString(context, FR_DEVICE, device);
        }else{
            Log.e(this.getClass().getSimpleName(), "deviceUUID and device FOUND!!!");
        }

        if (FirebaseInstanceId.getInstance().getToken() == null) {
            // do nothing
            Log.e(this.getClass().getSimpleName(), "token not found. ");
        } else {
            String token = FirebaseInstanceId.getInstance().getToken();
            ResFR.setPrefString(context, ResFR.FR_TOKEN, token);
//            ResFR.setPrefString(context, ResFR.FR_TOKEN, token);
            Log.e(this.getClass().getSimpleName(), "token get success. " + token);
            Log.e(this.getClass().getSimpleName(), "token shared preference saved success. " + ResFR.getPrefString(context, ResFR.FR_TOKEN));

            timerTask.cancel();

            finish();
            startActivity(new Intent(context, ActivityLogIn.class));
        }



    }


}
