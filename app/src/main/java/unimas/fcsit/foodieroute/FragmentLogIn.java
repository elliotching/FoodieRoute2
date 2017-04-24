package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Timer;

public class FragmentLogIn extends Fragment {

    //    public static boolean isOpened = false;
//    public static String isOpen_StringKey = "isOpen";
    Context context;
    Fragment mFragment;
    AppCompatActivity activity;
    Timer timer = new Timer();
    String mToBeDisplayMsg = "";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button mLoginButton;
    private Button buttonSignUp;
    private EditText editTextUsername , editTextPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = this.getContext();
        activity = (AppCompatActivity) context;
        mFragment = this;

        View view = inflater.inflate(R.layout.fragment_one_log_in, container,
                false);

        /*********************************************************
         * HOW TO GET ARGUMENTS::
         * RESULT = mFragment.getArguments().get*Boolean( KEY );
         *   or        <this>.getArguments().get*Boolean( KEY );
         *********************************************************/
        mLoginButton = (Button) view.findViewById(R.id.m_login_button);
        mLoginButton.setOnClickListener(new Click());

        editTextUsername = (EditText) view.findViewById(R.id.edit_text_username);
        editTextPassword = (EditText) view.findViewById(R.id.edit_text_password);
        editTextPassword.setOnEditorActionListener(new Click());

        buttonSignUp = (Button) view.findViewById(R.id.button_sign_up);
        buttonSignUp.setOnClickListener(new Click());
//        mLoginButton.setTransformationMethod(null);
//        this.getArguments().getBoolean()
//        activity.getSupportActionBar().addOnMenuVisibilityListener();

        activity.supportInvalidateOptionsMenu();


        return view;
    }

    public void stopAsyncTask() {
        timer.cancel();
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
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if(username.matches("")||password.matches("")){
            new Dialog_AlertNotice(context, "Error", "Please fill in all fields.").setPositiveKey("OK", null);
        }
        else{
            RequestParams r = new RequestParams();
            r.put("pass","!@#$");
            r.put("table", "users_testing");
            r.put("username",username);
            r.put("password",md5(password));

            String[][] data = new String[][]{
                    {"pass","!@#$"},
                    {"pass","!@#$"},
                    {"username",username},
                    {"password",md5(password)},
                    {"password",md5(password)},
                    {"password",md5(password)},
                    {"password",md5(password)},
                    {"password",md5(password)},
                    {"password",md5(password)}

            };
//            new Dialog_AlertNotice(context, "Result", md5(password)+"\n"+md5(password)).setPositiveKey("OK", null).show();

            new AsyncHTTPPost(ResFR.URL_login, r, true, new InterfaceAsyncHTTPPost() {

                final Dialog_Progress pd = new Dialog_Progress((AppCompatActivity) context,"Login", "Authenticating...", false);
                @Override
                public void notifyHTTPSuccess(int code, String result) {
                    pd.dismiss();
                    new Dialog_AlertNotice(context, "Result", "Login: "+result).setPositiveKey("OK", null);
                }

                @Override
                public void notifyHTTPFailure(int code, String result) {
                    pd.dismiss();
                    new Dialog_AlertNotice(context, "Error "+code, result).setPositiveKey("OK", null);
                }

                @Override
                public void notifyJSONArraySuccess(JSONArray jsonarray) {
                    pd.dismiss();
//                    new Dialog_AlertNotice(context, "Result", jsonarray.toString()).setPositiveKey("OK", null).show();
                    try{
                        if(jsonarray.length() == 1) {
                            ArrayList<JSONObject> a = new ArrayList<>();
                            for (int i = 0; i < 1; i++) {
                                a.add(jsonarray.getJSONObject(i));
                                JSONObject jsonobj = jsonarray.getJSONObject(i);
                                String username = jsonobj.optString("username");
                                SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
                                pref.edit().putString("FR_username", username).commit();
                                new AsyncTokenSaveUpdate(context);
                            }
                        }else{
                            if(jsonarray.length() >= 2)
                                new Dialog_AlertNotice(context, "Fail", "Somethings went wrong. More than 1 username(s) received.").setPositiveKey("OK", null);
                            if(jsonarray.length() == 0)
                                new Dialog_AlertNotice(context, "Fail", "Somethings went wrong. No username(s) received.").setPositiveKey("OK", null);
                        }
                    }catch (Exception e){

                    }
                }
            });

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

    private class DoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }


}