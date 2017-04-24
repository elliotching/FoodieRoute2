package unimas.fcsit.foodieroute;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Elliot on 03-Sep-16.
 */
public class ActivitySignUp extends AppCompatActivity {
    Context context = this;
    AppCompatActivity activity = this;

    EditText editUsername, editEmail, editPassword, editConfirmPassword;
    Button signUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new UniversalLayoutInitToolbarAndTheme(this, R.layout.activity_sign_up, R.id.toolbar_activity_sign_up, true);

        editUsername = (EditText) findViewById(R.id.edit_text_username);
        editEmail = (EditText) findViewById(R.id.edit_text_email);
        editPassword = (EditText) findViewById(R.id.edit_text_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_text_password_confirm);
        editConfirmPassword.setOnEditorActionListener(new OnClickEvent());
        signUp = (Button) findViewById(R.id.button_sign_up);
        signUp.setOnClickListener(new OnClickEvent());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    class OnClickEvent implements View.OnClickListener,TextView.OnEditorActionListener {

        @Override
        public void onClick(View view) {
            doSubmit();
        }

        @Override
        public boolean onEditorAction(TextView textView, int editor, KeyEvent keyEvent) {
            if (editor == EditorInfo.IME_ACTION_DONE) {
                doSubmit();
            }
            return false;
        }

        void doSubmit(){
            final Dialog_Progress pd = new Dialog_Progress(activity,
                    ResFR.string(context, R.string.s_prgdialog_title_sign_up),
                    ResFR.string(context, R.string.s_prgdialog_submitting_signup_form), false);
            String username = editUsername.getText().toString();
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            String confirm = editConfirmPassword.getText().toString();
            if(username.matches("")||email.matches("")||password.matches("")||confirm.matches("")){
                pd.dismiss();
                new Dialog_AlertNotice(context, "Error", "Please fill in all fields.").setPositiveKey("OK",null);
            }else if(!password.equals(confirm)){
                pd.dismiss();
                new Dialog_AlertNotice(context, "Error", "Password does not matched.").setPositiveKey("OK",null);
            }
            else{
                RequestParams r = new RequestParams();
                r.put("pass","!@#$");
                r.put("table","users_testing");
                r.put("username",username);
                r.put("email",email);
                r.put("password",md5(password));
                r.put("confirm",md5(confirm));
                new AsyncHTTPPost(ResFR.URL_reg_user, r, false, new InterfaceAsyncHTTPPost() {
                    @Override
                    public void notifyHTTPSuccess(int code, String result) {
                        pd.dismiss();
                        new Dialog_AlertNotice(context,"Result", result).setPositiveKey("OK", null);
                    }

                    @Override
                    public void notifyHTTPFailure(int code, String result) {
                        pd.dismiss();
                        new Dialog_AlertNotice(context,"Error "+code, result).setPositiveKey("OK", null);
                    }

                    @Override
                    public void notifyJSONArraySuccess(JSONArray jsonarray) {

                    }
                });
            }
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
}
