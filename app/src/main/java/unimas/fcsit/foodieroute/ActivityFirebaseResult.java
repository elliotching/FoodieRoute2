package unimas.fcsit.foodieroute;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by elliotching on 02-Mar-17.
 */

public class ActivityFirebaseResult extends AppCompatActivity {
    private TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        textView = (TextView)findViewById(R.id.textView1);
        textView.setText("Welcome to the Result Activity");
    }
}