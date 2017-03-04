package unimas.fcsit.foodieroute;

/**
 * Created by elliotching on 08-Dec-16.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Elliot on 12-Aug-16.
 */
public class Add_Food extends AppCompatActivity {

    Context mContext = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.m_add_food_toolbar);
//        toolbar.setTitle(R.string.m_title_label_add_food);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        final TypedArray styledAttributes = mContext.getTheme().obtainStyledAttributes(
//                new int[] { android.R.attr.actionBarSize });
//        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
//        styledAttributes.recycle();
//        Toast.makeText(mContext , String.valueOf(mActionBarSize) , Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
