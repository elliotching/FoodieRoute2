package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.UUID;

/**
 * Created by elliotching on 28-Jun-16.
 */
public class SplashScreen extends AppCompatActivity {

    Context context = this;

    public void onCreate(Bundle ins) {
        super.onCreate(ins);
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        finish();
        startActivity(new Intent(context, Foodie_main.class));
//        runSplashwithDelayed(this, 200);
    }




}
