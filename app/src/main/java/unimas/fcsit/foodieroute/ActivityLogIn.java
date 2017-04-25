package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;

/**
 * Created by elliotching on 25-Apr-17.
 */

public class ActivityLogIn extends AppCompatActivity {
    Context context = this;
    AppCompatActivity activity = (AppCompatActivity) context;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button mLoginButton;
    private Button buttonSignUp;
    private EditText editTextUsername , editTextPassword;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new UniversalLayoutInitToolbarAndTheme(activity, R.layout.activity_log_in, R.id.toolbar, false);

        Bundle bundle = getIntent().getExtras();
        boolean kicked = false;
        if(bundle != null) {
            kicked = bundle.getBoolean(ResFR.BUNDLE_KEY_KICKED_OUT, false);
        }

        if(!kicked)
            new AsyncCheckLogInStatus(context);
        else{
            new Dialog_AlertNotice(context, R.string.s_dialog_title_warning, R.string.s_dialog_msg_kicked_out)
                    .setPositiveKey(R.string.s_dialog_btn_ok, null);
        }
        /*********************************************************
         * HOW TO GET ARGUMENTS::
         * RESULT = mFragment.getArguments().get*Boolean( KEY );
         *   or        <this>.getArguments().get*Boolean( KEY );
         *********************************************************/
        mLoginButton = (Button) findViewById(R.id.m_login_button);
        mLoginButton.setOnClickListener(new Click());

        editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        editTextPassword.setOnEditorActionListener(new Click());

        buttonSignUp = (Button) findViewById(R.id.button_sign_up);
        buttonSignUp.setOnClickListener(new Click());

    }

    private class Click implements View.OnClickListener , TextView.OnEditorActionListener {

        @Override
        public void onClick(View v) {
            if(v==mLoginButton){
                doLogin();
            }
            if(v==buttonSignUp){
                startActivity(new Intent(context, ActivitySignUp.class));
            }
        }

        @Override
        public boolean onEditorAction(TextView textView, int editor, KeyEvent keyEvent) {
            if(editor == EditorInfo.IME_ACTION_DONE){
                doLogin();
            }
            return false;
        }
    }

    private void doLogin(){
        Dialog_Progress p = new Dialog_Progress(activity, R.string.s_prgdialog_title_log_in,
                R.string.s_prgdialog_log_in_authenticate, false);

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if(username.matches("")||password.matches("")){
            new Dialog_AlertNotice(context, "Error", "Please fill in all fields.").setPositiveKey("OK", null);
        }
        else{

            String deviceUUID = ResFR.getPrefString(context, ResFR.FR_DEVICEUUID);
            String device = ResFR.getPrefString(context, ResFR.FR_DEVICE);

            String token = FirebaseInstanceId.getInstance().getToken();
            ResFR.setPrefString(context, ResFR.FR_TOKEN, token);
            token = ResFR.getPrefString(context, ResFR.FR_TOKEN);

            String[][] data = new String[][]{
                    {"pass","!@#$"},
                    {"username",username},
                    {"password",md5(password)},
                    {"deviceUUID",deviceUUID},
                    {"device",device},
                    {"token",token}
            };

            new HTTP_POST(context, data, ResFR.URL_log_in, p);
        }
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private class HTTP_POST implements InterfaceCustomHTTP{
        Dialog_Progress p;
        HTTP_POST(Context c, String[][] d, String url, Dialog_Progress p){
            this.p = p;
            CustomHTTP cc = new CustomHTTP(c, d, url);
            cc.ui = this;
            cc.execute();
        }
        @Override
        public void onCompleted(String result) {
            p.dismiss();
            try {
                JSONObject json = new JSONObject(result);
                String username = json.optString("username");
                String activated = json.optString("activated");

                ResFR.setPrefString(context, ResFR.FR_USERNAME, username);
            } catch (JSONException e) {
                e.printStackTrace();

                String warning = ResFR.string(context, R.string.s_dialog_title_warning);
                new Dialog_AlertNotice(context, warning, result)
                        .setPositiveKey(R.string.s_dialog_btn_ok, null);
            }
        }
    }

}
