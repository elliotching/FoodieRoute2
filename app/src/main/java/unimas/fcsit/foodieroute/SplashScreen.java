package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by elliotching on 28-Jun-16.
 */
public class SplashScreen extends AppCompatActivity {

    public void onCreate(Bundle ins) {
        super.onCreate(ins);
        runSplashwithDelayed(this, 1000);
    }

    private void runSplashwithDelayed(Context c, int milliseconds){
        final int splashTimeOut = milliseconds;
        final Context context = c;
        Thread splashThread = new Thread() {
            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    while (wait < splashTimeOut) {
                        sleep(100);
                        wait += 100;
                    }
                }
                catch (Exception e) {
                }
                finally {
                    finish();
                    startActivity(new Intent(context, Foodie_main.class));
                }
            }
        };
        splashThread.start();
    }
}
