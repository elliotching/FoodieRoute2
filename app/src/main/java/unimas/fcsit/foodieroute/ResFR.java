package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;

/**
 * Created by elliotching on 24-Feb-17.
 */

public class ResFR {

    private final Context context;
    static final String PREF_NAME = "FoodieRoute";
    static final String FR_DEVICEUUID = "FR_deviceUUID";
    static final String FR_THEME = "FR_theme";
    static final String FR_TOKEN = "FR_token";
    static final String FR_USERNAME = "FR_username";
    static final String FR_DEVICE = "FR_device";
    static final String FR_FIREBASE_INSTANCE_ID = "FR_firebase_IID";
    static final String DEFAULT_EMPTY = "empty";

    public static final String BUNDLE_KEY_KICKED_OUT = "kicked_out";

    public static final String URL_send_mesg = "http://foodlocator.com.my/mobile/send_mesg.php";
    public static final String URL_get_token = "http://foodlocator.com.my/mobile/get_token.php";
    public static final String URL_sign_up = "http://foodlocator.com.my/mobile/sign_up.php";
    public static final String URL_insert_token = "http://foodlocator.com.my/mobile/insert_token.php";
    public static final String URL_log_in = "http://foodlocator.com.my/mobile/log_in.php";
    public static final String URL_check_log_in_status = "http://foodlocator.com.my/mobile/check_log_in_status.php";
    public static final String URL_on_token_refresh = "http://foodlocator.com.my/mobile/on_token_refresh.php";
    public static final String URL_upload = "http://foodlocator.com.my/mobile/imgupload/upload.php";
    public static final String URL_add_food = "http://foodlocator.com.my/mobile/add_food.php";
    public static final String URL_read_image = "http://foodlocator.com.my/mobile/imgupload/read_image.php";

    ResFR(Context c){
        context = c;
    }

    String string(int _R){
        return context.getResources().getString(_R);
    }

    Resources res(){
        return context.getResources();
    }

    int color(int _R){
        return ResourcesCompat.getColor(context.getResources(),_R,null);
    }

    static int dimenPx(Context context,int _d){
        return context.getResources().getDimensionPixelSize(_d);
    }

    static String string(Context context, int _R){
        return context.getResources().getString(_R);
    }

    static String getPrefString(Context context, String keyName){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        return pref.getString(keyName, DEFAULT_EMPTY);
    }

    static void setPrefString(Context context, String keyName, String value){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit().putString(keyName, value);
        editor.commit();
    }
}
