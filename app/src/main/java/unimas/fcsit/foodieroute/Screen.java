package unimas.fcsit.foodieroute;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by elliotching on 21-Mar-17.
 */

public class Screen {
//
//    static Context context;
//    static DisplayMetrics metrics;
//    Screen(Context c){
//        context = c;
//    }

//    Screen(Context context){
//        this.context = context;
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//    }

    static int getPixels(Context context,  float unitDP){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = unitDP;
        float fpixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
        int pixels = Math.round(fpixels);
        return pixels;
    }

    static int getWidth(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }
    static float getDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    static float getScaledDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.scaledDensity;
    }
}
