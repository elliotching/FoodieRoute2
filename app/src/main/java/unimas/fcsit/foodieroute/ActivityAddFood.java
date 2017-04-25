package unimas.fcsit.foodieroute;

/**
 * Created by elliotching on 08-Dec-16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import unimas.fcsit.foodieroute.ActivityMaps;
import unimas.fcsit.foodieroute.ActivityPickImage;
import unimas.fcsit.foodieroute.Dialog_AlertNotice;
import unimas.fcsit.foodieroute.ImageUpload;
import unimas.fcsit.foodieroute.InterfaceUploadListener;
import unimas.fcsit.foodieroute.R;
import unimas.fcsit.foodieroute.UniversalLayoutInitToolbarAndTheme;

/**
 * Created by Elliot on 12-Aug-16.
 */
public class ActivityAddFood extends AppCompatActivity {

    Context context = this;
    private static final int PICK_IMAGE_CODE = 9123;

    // pick location code
    public static final int PICK_LOC_CODE = 768;


    private EditText editTextFoodName;
    private EditText editTextFoodPrice;
    private Button buttonPickImage;
    private Button buttonChosenLocation1;
    private Button buttonChosenLocation2;
    private Button buttonSubmit;
    private CheckBox checkBoxMobileSeller;


    private String[] arrayImage;
    // arrayImage[0] is FULL PATH (/sdcard/.../foordieroute-wq8123.jpg)
    // arrayImage[1] is IMAGE NAME

    private double[] pickedLocation;

    UploadListener uplaodListener = new UploadListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new UniversalLayoutInitToolbarAndTheme(this, R.layout.activity_add_food,R.id.toolbar_add_food,true);

        editTextFoodPrice = (EditText) findViewById(R.id.edittext_food_price);
        editTextFoodName = (EditText) findViewById(R.id.edittext_food_name);
//        buttonChosenLocation1 = (Button) findViewById(R.id.button_choose_location);
        buttonPickImage = (Button)findViewById(R.id.button_pick);
        buttonSubmit = (Button) findViewById(R.id.button_submit);


        buttonChosenLocation1 = (Button) findViewById(R.id.button_choose_location);
        buttonChosenLocation2 = (Button) findViewById(R.id.button_choose_location_2);

        checkBoxMobileSeller = (CheckBox) findViewById(R.id.checkbox_mobile_seller);

        buttonChosenLocation1.setVisibility(View.VISIBLE);
        buttonChosenLocation2.setVisibility(View.GONE);

        buttonPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ActivityPickImage.class);
                startActivityForResult(i, PICK_IMAGE_CODE);
            }
        });



        View.OnClickListener onMapClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 go to map
                Intent i = new Intent(context, ActivityMaps.class);
                startActivityForResult(i, PICK_LOC_CODE);

            }
        };
        buttonChosenLocation1.setOnClickListener(onMapClick);

        checkBoxMobileSeller.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    // YES, I'm moving everyday.
                    buttonChosenLocation1.setVisibility(View.GONE);
                    buttonChosenLocation2.setVisibility(View.VISIBLE);
                }
                else{
                    // NO, I got static selling location.
                    buttonChosenLocation1.setVisibility(View.VISIBLE);
                    buttonChosenLocation2.setVisibility(View.GONE);
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dialog_AlertNotice(context, R.string.s_dialog_title_nothing_here, R.string.s_dialog_msg_to_be_implement)
                        .setPositiveKey(R.string.s_dialog_btn_ok, null);

                double i = 37.023;
                double a = i % 1;
            }
        });
    }

    // not using so far
//    private void adjustImageView(){
//        LinearLayout.LayoutParams layoutParams =
//                (LinearLayout.LayoutParams) pickedImage.getLayoutParams();
//
//        DisplayMetrics d = context.getResources().getDisplayMetrics();
//        // w_dp = 200, means i want 200 dp
//        float w_dp = 200; //200dp
//        float h_dp = w_dp * 3 / 4;
//        int w = Screen.getPixels(context, w_dp);
//        int h = Screen.getPixels(context, h_dp);
//
//        layoutParams.width = w;
//        layoutParams.height = h;
//        layoutParams.gravity = Gravity.CENTER;
//
//        pickedImage.setLayoutParams(layoutParams);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE){
            arrayImage = data.getExtras().getStringArray("filenamearray");
            if (arrayImage != null) {
                buttonPickImage.setText(arrayImage[1]);
            }
        }
        else if(requestCode == PICK_IMAGE_CODE){
            if(arrayImage == null){
                setButtonDefaultTextNoImageChoosen(buttonPickImage);
            }
        }


        // return from map activity
        if(resultCode == RESULT_OK && requestCode == PICK_LOC_CODE){
            pickedLocation = data.getExtras().getDoubleArray("savedlocation");
            if(pickedLocation == null){
                setButtonDefaultTextNoLocationChoosen(buttonChosenLocation1);
            }else{
                Log.e("Elliot", "returned Location success.");
                buttonChosenLocation1.setText(""+String.format("%.10f",pickedLocation[0])+" , "+
                        String.format("%.10f",pickedLocation[1]));
            }
        }else if(resultCode == RESULT_CANCELED && requestCode == PICK_LOC_CODE){
            if(pickedLocation == null) {
                setButtonDefaultTextNoLocationChoosen(buttonChosenLocation1);
            }
        }
    }

    private void setButtonDefaultTextNoImageChoosen(Button s){
        s.setText(R.string.s_button_no_image_choosen);
    }
    private void setButtonDefaultTextNoLocationChoosen(Button s){
        s.setText(R.string.s_button_no_location_choosen);
    }



    // When ImageUpload button is clicked
    public void uploadImage(View v) {
        // When Image is selected from Gallery

        // arrayImage
        // from onActivityResult
        if (arrayImage != null) {
            String imgPath = arrayImage[0];
            String filename = arrayImage[1];
            if (imgPath != null && !imgPath.isEmpty()) {

                // Convert image to String using Base64
                ImageUpload.encodeImagetoString(context, imgPath , filename, uplaodListener);
                // When Image is not selected from Gallery
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Can't found image",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {

        finish();
        return super.onSupportNavigateUp();
    }


    private class UploadListener implements InterfaceUploadListener {
        @Override
        public void onFinishedUpload() {
            // when upload onFinishedUpload
            // when upload finish
            // do submit food data.

            // get image from array instead of button text
            String image_file_name = arrayImage[1];
            String food_name = editTextFoodName.getText().toString();
            String food_price = editTextFoodPrice.getText().toString();
            String seller_location_lat = String.format("%.10f", pickedLocation[0]);
            String seller_location_lng = String.format("%.10f", pickedLocation[1]);

        }
    }
}
