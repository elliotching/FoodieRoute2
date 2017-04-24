package unimas.fcsit.foodieroute;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by Elliot on 19-Aug-16.
 */
public class AdapterFoodListingListViewElliot extends BaseAdapter {

    Context context;
    ArrayList<FoodListingObject> data;
    private static final String url_read_image = ResFR.URL_read_image;

    public AdapterFoodListingListViewElliot(Context context, ArrayList<FoodListingObject> data) {
        this.data = data;
        this.context = context;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemHolder holder;
        View view = convertView;

        if (view == null) {
            holder = new ItemHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_view_single_item_layout,
                    null
//                    parent
            );

            holder.innerTextView = (TextView) view.findViewById(R.id.textview_adapter_list_view_food_name);
            holder.imageView = (ImageView) view.findViewById(R.id.imageview_adapter_list_view);
            holder.textViewFoodPrice = (TextView) view.findViewById(R.id.textview_adapter_list_view_food_price);
            view.setTag(holder);
        } else {
            holder = (ItemHolder) view.getTag();
        }


        // set text for food name.
        holder.innerTextView.setText(data.get(position).foodName);

        // load image from web.
        String imgURL = url_read_image + "?image_name=" + data.get(position).photoName;
        Log.d("ElliotImageURL", imgURL);
        Ion.with(context)
                .load(imgURL)
                .withBitmap()
                .placeholder(R.mipmap.loading)
                .intoImageView(holder.imageView);

        holder.textViewFoodPrice.setText("RM "+data.get(position).foodPrice);

        return view;
    }

    private class ItemHolder {
        TextView innerTextView;
        ImageView imageView;
        TextView textViewFoodPrice;
    }
}
//        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    Context context;
//    ArrayList<FoodListingObject> mDataArrayList;
//
//    public AdapterFoodListingListViewElliot(Context c, ArrayList<FoodListingObject> data) {
//        context = c;
//        mDataArrayList = data;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        final View view = LayoutInflater.from(context).inflate(R.layout.list_view_single_item_layout, parent, false);
//        ElliotHolder h = new ElliotHolder(view);
//        return new ElliotHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ElliotHolder elliotHolder = (ElliotHolder) holder;
//        elliotHolder.thisTextView = (TextView) elliotHolder.thisItemView.findViewById(R.id.m_list_view_item_text_view);
//        elliotHolder.thisI = (ImageView) elliotHolder.thisItemView.findViewById(R.id.image);
//        elliotHolder.thisTextView.setText(mDataArrayList.get(position).text);
////        elliotHolder.thisTextView.setBackgroundResource(mDataArrayList.get(position).colorID);
//        elliotHolder.thisI.setImageResource(mDataArrayList.get(position).photoRes);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDataArrayList.size();
//    }
//
//    private class ElliotHolder extends RecyclerView.ViewHolder {
//        TextView thisTextView;
//        ImageView thisI;
//        View thisItemView;
//
//        public ElliotHolder(View itemView) {
//            super(itemView);
//            thisItemView = itemView;
//        }
//    }
//
//}