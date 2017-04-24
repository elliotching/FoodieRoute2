package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by elliotching on 09-Mar-17.
 */

public class ActivitySetting extends AppCompatActivity {
    Context context = this;
    AppCompatActivity activity= this;
    RadioGroup radioGroup;
    RadioButton radioLight;
    RadioButton radioDark;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        String themeSetting = pref.getString("FR_theme", "Light");
        if(themeSetting.equals("Light")){
            pref.edit().putString("FR_theme","Light");
            pref.edit().commit();
        }
        else if(themeSetting.equals("Dark")){
            context.setTheme(R.style.AppThemeDark);
        }
        System.out.println("theme = "+ themeSetting);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_setting);
        if(themeSetting.equals("Dark"))toolbar.setPopupTheme(R.style.app_bar_white_text_dark_elliot);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUIsetup();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), Foodie_main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Build.VERSION.SDK_INT > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            Intent intent = new Intent(getApplicationContext(), Foodie_main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initUIsetup(){


        radioGroup = (RadioGroup) findViewById(R.id.radiogrp_theme_activity_setting);

        radioLight = (RadioButton) findViewById(R.id.radiobutton_theme_light_activity_setting);
        radioDark = (RadioButton) findViewById(R.id.radiobutton_theme_dark_activity_setting);

        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        String theme = pref.getString("FR_theme","Light");
        if(theme.equals("Dark")) radioDark.setChecked(true);

        radioLight.setOnClickListener(new OnClick());

        radioDark.setOnClickListener(new OnClick());


    }

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefedit = pref.edit();
            if(view == radioLight){
                prefedit.putString("FR_theme","Light");
                prefedit.commit();

                Intent intent = new Intent(context, ActivitySetting.class);
                startActivity(intent);
                finish();
            }

            if(view == radioDark){
                prefedit.putString("FR_theme","Dark");
                prefedit.commit();

                Intent intent = new Intent(context, ActivitySetting.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
