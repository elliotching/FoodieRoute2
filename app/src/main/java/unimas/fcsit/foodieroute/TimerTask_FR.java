package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by elliotching on 15-Mar-17.
 */

public class TimerTask_FR extends java.util.TimerTask {

    final Handler handler;
    final Timer timer;
    final int endingTime;
    static int time = 0;


    TimerTask_FR(int countFor_Seconds) {
        handler = new Handler();
        timer = new Timer();
        endingTime = countFor_Seconds * 1000;
        timer.schedule(this, 0, 100);
    }

    @Override
    public void run() {
        handler.post(new FRRunnable());
    }


    class FRRunnable implements Runnable {
        @Override
        public void run() {
            try {
//                time += 100;
//                if (time == 100) { // starting point
//                    AsyncRequestData.progressDialog.setMessage("Press again to cancel.");
//                } else if (time >= endingTime) { // ending point
//                    AsyncRequestData.progressDialog.setMessage("Loading...");
//                    timer.cancel();
//                    time = 0;
//                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
            }
        }
    }

    private void checkProgDialog(){
//        if (AsyncRequestData.progressDialog.isShowing()) {
//            if (time >= 100 && time < 3000 && AsyncRequestData.progressDialog.isShowing()) {
//                AsyncRequestData.progressDialog.dismiss();
//                AsyncRequestData.cancelRequest();
//            } else {
////                        startCountPressAgainExit(3);
//                new TimerTask_FR(3).start();
//            }
//        }
    }

    private void runSplashwithDelayed(Context c, int milliseconds) {
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
                } catch (Exception e) {
                } finally {

                }
            }
        };
        splashThread.start();
    }
}