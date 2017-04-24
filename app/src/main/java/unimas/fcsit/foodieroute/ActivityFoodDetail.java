package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by elliotching on 08-Mar-17.
 */

public class ActivityFoodDetail extends AppCompatActivity {

    Context context = this;
    AppCompatActivity activity = this;
    TextView textViewFoodName;
    ImageView imageViewFoodImage;
    TextView textViewFoodPrice;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new UniversalLayoutInitToolbarAndTheme(this, R.layout.activity_food_detail, R.id.toolbar, true);

        Intent i = activity.getIntent();
        String foodName = i.getStringExtra("FoodListingObjectText");
        int foodImgRes = i.getIntExtra("FoodListingObjectImage", 0);
        String foodPrice = i.getStringExtra("FoodListingObjectPrice");

        textViewFoodName = (TextView) findViewById(R.id.textview_foodname_activity_food_detail);
        textViewFoodPrice = (TextView) findViewById(R.id.textview_foodprice_activity_food_detail);
        imageViewFoodImage = (ImageView) findViewById(R.id.imageview_foodimage_activity_food_detail);

        imageViewFoodImage.setImageResource(foodImgRes);
        textViewFoodName.setText(foodName);
        textViewFoodPrice.setText("RM "+foodPrice);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
