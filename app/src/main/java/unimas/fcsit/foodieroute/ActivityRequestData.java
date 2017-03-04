package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by elliotching on 23-Feb-17.
 */

public class ActivityRequestData extends AppCompatActivity {

    Context mContext = this;
    Toolbar mToolbar;
    EditText mEdittextURL;
    Button mButtonstartconnection;
    TextView mTextviewResult;
    private static final String privateurl = "http://foodlocator.com.my/mobile/test_s.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_data);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_apicall);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new ImplementedAsyncRequestData();

        mEdittextURL = (EditText) findViewById(R.id.edittext_apicall_ip_address);
        mEdittextURL.setText(privateurl);

        mButtonstartconnection = (Button) findViewById(R.id.button_start_apiconnection);
        mButtonstartconnection.setOnClickListener(new ButtonStart());

//        mTextviewResult = (TextView) findViewById(R.id.textview_apiresult);
    }

    private class ButtonStart implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view == mButtonstartconnection) {
                RequestParams params = new RequestParams();
                params.put("att", "*");
                params.put("table", "Foods");
                AsyncRequestData.makeHTTPCall(mContext, "Loading...", params);
            }
        }
    }


    public static class BackKey implements DialogInterface.OnKeyListener {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if(AsyncRequestData.progressDialog.isShowing()) {
                    if (time >= 100 && time < 3000) {
                        AsyncRequestData.progressDialog.dismiss();
                        AsyncRequestData.cancelRequest();
                    } else {
                        startCountPressAgainExit(3);
                    }
                }
            }
            return false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }




    static int time = 0;

    public static void startCountPressAgainExit(int countFor_Seconds) {
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        final int endingTime = countFor_Seconds*1000;
        TimerTask doAsynchronousTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            Log.d("elliot", String.valueOf(time));
                            time += 100;
                            if (time == 100) {
                                AsyncRequestData.progressDialog.setMessage("Press again to cancel.");
                            } else if (time >= endingTime) {
                                AsyncRequestData.progressDialog.setMessage("Loading...");
                                timer.cancel();
                                time = 0;
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 100); //execute in every 50000 ms
    }


    private void doListView(ArrayList<JSONObject> responses) {
        try {
            Toast.makeText(mContext, responses.get(0).toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(mContext, "Failed", Toast.LENGTH_LONG).show();
        }
    }


    private class ImplementedAsyncRequestData implements InterfaceAsyncRequestData {

        ImplementedAsyncRequestData() {
            AsyncRequestData.activityRequestData = this;
        }

        @Override
        public void getArrayListJSONResult(ArrayList<JSONObject> responses) {
            doListView(responses);
        }
    }
}
