package unimas.fcsit.foodieroute;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by elliotching on 07-Mar-17.
 */

public class Dialog_CustomNotice {
    AlertDialog d;
    TextView text;


    Dialog_CustomNotice(Context context) {
        d = new AlertDialog.Builder(context).create();
//        AlertDialog.Builder dd = d;
//        String string_InputActivCode = ResFR.string(context, R.string.s_label_dialog_input_activation_code);
//        String string_CheckJunkMail = ResFR.string(context, R.string.s_label_dialog_activation_check_junk_mail);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_input_activation_key, null);
        EditText editTextActiveationCode = (EditText) v.findViewById(R.id.edit_text_activation_code);
        editTextActiveationCode.setOnEditorActionListener(new OnEditorAction());
        String string_OK = ResFR.string(context, R.string.s_dialog_btn_ok);

        d.setButton(DialogInterface.BUTTON_POSITIVE, string_OK, new OnOKClick());
        d.setView(v);
        d.setTitle("Account Activation");
        d.setCancelable(false);
    }

    private void validateActivationCode(){
        Log.e("Elliot", "Clicked...");
    }

    private class OnEditorAction implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if(i == EditorInfo.IME_ACTION_DONE){
                validateActivationCode();
                d.dismiss();
            }
            return false;
        }
    }

    private class OnOKClick implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int button) {
            if(button == DialogInterface.BUTTON_POSITIVE){
                validateActivationCode();
            }
        }
    }

    Dialog_CustomNotice setPositiveKey(String positiveKey, DialogInterface.OnClickListener onClick) {
        d.setButton(DialogInterface.BUTTON_POSITIVE, positiveKey, onClick);
        return this;
    }

    Dialog_CustomNotice setNegativeKey(String negativeKey, DialogInterface.OnClickListener onClick) {
        d.setButton(DialogInterface.BUTTON_NEGATIVE, negativeKey, onClick);
        return this;
    }

    void show() {
        d.show();
    }
}

    // Retrieve from LandingPage HouseNearby (focus on how to customize view layout in coding)
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        ViewHolder holder;
//
//        // check if the view already exists
//        // if so, no need to inflate and findViewById again!
//
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int screenWidth_px = displayMetrics.widthPixels;
//        double profileWidth_px = screenWidth_px / 4.0;
//        double profileHeight_px = profileWidth_px * 4.0 / 3.0;
//
//        if (convertView == null) {
//
//            // Inflate the custom row layout from your XML.
//            convertView = mInflater.inflate(R.layout.lawyer_row_0317v2, null);
//
//            // create a new "Holder" with subviews
//            holder = new ViewHolder();
//
//            holder.thumbnailImageView = (NetworkImageView) convertView.findViewById(R.id.lawyer_image);
//            holder.thumbnailImageView.setLayoutParams( new RelativeLayout.LayoutParams( (int)profileWidth_px , (int)profileHeight_px ) );
//
//            holder.nameTextView = (TextView) convertView.findViewById(R.id.lawyer_name);
////            holder.nameTextView.setTypeface(null , Typeface.BOLD);
//
//            holder.companyTextView = (TextView) convertView.findViewById(R.id.lawyer_company);
//            holder.locationTextView = (TextView) convertView.findViewById(R.id.lawyer_location);
//
//            holder.lawyerPracticeArea = (LinearLayout) convertView.findViewById(R.id.lawyer_practice_area);
//
//            // hang onto this holder for future recyclage
//            convertView.setTag(holder);
//        } else {
//
//            // skip all the expensive inflation/findViewById
//            // and just get the holder you already made
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        ImageCache_ImageLoader imageLoader = ImageCacheManager.getInstance(context).getImageLoader();
//        holder.thumbnailImageView.setDefaultImageResId(R.drawable.default_lawyer_profile);
//        holder.thumbnailImageView.setImageUrl(list.get(i).getURL(), imageLoader);
//        holder.nameTextView.setText(list.get(i).getName());
//        holder.companyTextView.setText(list.get(i).getCompany());
//        holder.locationTextView.setText(list.get(i).getLocation());
//
////        int practiceAreaSize = list.get(i).practice_area.length == 0?1:list.get(i).practice_area.length;
//        holder.lawyerPracticeArea.removeAllViews();
//        for(int ii = 0 ; ii < list.get(i).practice_area.length ; ii++){
////            System.out.println("========================");
////            System.out.println("list.get("+i+").getName() : "+list.get(i).getName());
////            System.out.println("practiceAreaSize : "+practiceAreaSize);
////            System.out.println("list.get("+i+").practice_area.length : "+list.get(i).practice_area.length);
//            holder.practiceAreaTextView = new TextView[list.get(i).practice_area.length];
//            holder.practiceAreaTextView[ii] = (TextView) mInflater.inflate(R.layout.lawyer_listing_practice_area_row_0317v2,null);
//
//            holder.lawyerPracticeArea.addView(holder.practiceAreaTextView[ii]);
//
//            {
////                System.out.println("list.get(" + i + ").practice_area.[" + ii + "] : " + list.get(i).practice_area[ii]);
//                holder.practiceAreaTextView[ii].setText(list.get(i).practice_area[ii]);
//
//            }
//        }
//
////        android:layout_width="match_parent"
////        android:layout_height="wrap_content"
////        style="@style/single_line_14sp"/>
//
//
//
//        return convertView;
//    }






    //Retrieve from Furniture Landing Page
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        ViewHolder holder;
//
//        // check if the view already exists
//        // if so, no need to inflate and findViewById again!
//
//        if (convertView == null) {
//
//            // Inflate the custom row layout from your XML.
//            convertView = mInflater.inflate(R.layout.row_furniture_2, null);
//
//
//            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//            // metrics give >>> width / height of the activity/application screen, NOT the entire PHONE Screen Width/Height
//            int width = metrics.widthPixels;
//
//            // create a new "Holder" with subviews
//            holder = new ViewHolder();
//
//            if(numColumns == 1) {
//                convertView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, width));
//
//                double gridViewPadding = context.getResources().getDimension(R.dimen.renovate_row_furniture_padding);
//                double activityPadding = context.getResources().getDimension(R.dimen.activity_horizontal_margin);
//                double imageWidth_px = (width) - (2 * gridViewPadding) - (2 * activityPadding);
//                double imageHeight_px = imageWidth_px /4.0*3.0;
//
//                holder.infoBoxLayout = (LinearLayout) convertView.findViewById(R.id.furn_grid_layout);
//                RelativeLayout.LayoutParams p =  new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , (int)(imageHeight_px/2.0));
//                p.addRule(RelativeLayout.BELOW , R.id.img_thumbnail);
//                holder.infoBoxLayout.setLayoutParams(p);
//
//                holder.networkImage = (NetworkImageView) convertView.findViewById(R.id.img_thumbnail);
//                RelativeLayout.LayoutParams q =  new RelativeLayout.LayoutParams( (int)imageWidth_px , (int)imageHeight_px );
//                q.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                holder.networkImage.setLayoutParams(q);
//
//                holder.priceTextView = (TextView) convertView.findViewById(R.id.text_result_price);
//                holder.brandTextView = (TextView) convertView.findViewById(R.id.text_result_brand);
//                holder.priceTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
//                holder.brandTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
//
//            }else{ // numColumns == 2 or more
//                convertView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
//
//                double gridViewPadding = context.getResources().getDimension(R.dimen.renovate_row_furniture_padding);
//                double activityPadding = context.getResources().getDimension(R.dimen.activity_horizontal_margin);
//                double imageWidth_px = (width / 2.0) - (2 * gridViewPadding) - activityPadding;
//                double imageHeight_px = imageWidth_px /4.0*3.0;
//
//                holder.infoBoxLayout = (LinearLayout) convertView.findViewById(R.id.furn_grid_layout);
//                RelativeLayout.LayoutParams p =  new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , (int)(imageHeight_px/1.5));
//                p.addRule(RelativeLayout.BELOW , R.id.img_thumbnail);
//                holder.infoBoxLayout.setLayoutParams(p);
//
//                holder.networkImage = (NetworkImageView) convertView.findViewById(R.id.img_thumbnail);
//                RelativeLayout.LayoutParams q =  new RelativeLayout.LayoutParams( (int)imageWidth_px , (int)imageHeight_px );
//                q.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                holder.networkImage.setLayoutParams(q);
//
//                holder.priceTextView = (TextView) convertView.findViewById(R.id.text_result_price);
//                holder.brandTextView = (TextView) convertView.findViewById(R.id.text_result_brand);
//                holder.priceTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
//                holder.brandTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
//
//            }
//
//            // hang onto this holder for future recyclage
//            convertView.setTag(holder);
//        } else {
//
//            // skip all the expensive inflation/findViewById
//            // and just get the holder you already made
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//
//        // Get the NetworkImageView that will display the image.
//        holder.networkImage = (NetworkImageView) convertView.findViewById(R.id.img_thumbnail);
//
//        // Get the ImageCache_ImageLoader through your singleton class.
//        ImageCache_ImageLoader mImageLoader = ImageCacheManager.getInstance(context).getImageLoader();
//
//        // Set the URL of the image that should be loaded into this view, and
//        // specify the ImageCache_ImageLoader that will be used to make the request.
//        holder.networkImage.setDefaultImageResId(R.drawable.default_furniture);
//        holder.networkImage.setImageUrl( list.get(i).getThumbnailURL() , mImageLoader );
//
//        holder.priceTextView.setText("RM "+list.get(i).getPrice());
//
//        holder.brandTextView.setText(list.get(i).getBrand());
//
//        return convertView;
//    }

