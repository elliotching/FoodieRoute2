package unimas.fcsit.foodieroute;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by elliotching on 16-Mar-17.
 */

class Dialog_Progress extends ProgressDialog {

    Dialog_Progress(AppCompatActivity activity, String title, String msg, boolean close){
        super(activity);
        this.setMessage(msg);
        this.setTitle(title);
        this.setCancelable(false);
        this.setOnKeyListener(new DeviceBackKey(activity, this, close));
        this.show();
    }

    Dialog_Progress(AppCompatActivity activity, int ResStringTitle, int ResMsg, boolean close){
        super(activity);
        ResFR r = new ResFR(activity);
        this.setMessage(r.string(ResMsg));
        this.setTitle(r.string(ResStringTitle));
        this.setCancelable(false);
        this.setOnKeyListener(new DeviceBackKey(activity, this, close));
        this.show();
    }
}
