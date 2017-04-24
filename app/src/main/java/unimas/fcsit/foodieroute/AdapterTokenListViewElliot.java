package unimas.fcsit.foodieroute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Elliot on 19-Aug-16.
 */
public class AdapterTokenListViewElliot extends BaseAdapter {

    Context context;
    ArrayList<ObjectToken> data;

    public AdapterTokenListViewElliot(Context context, ArrayList<ObjectToken> data) {
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
            view = LayoutInflater.from(context).inflate(R.layout.list_view_single_item_layout_activity_firebase_test, null);
            holder.textUser = (TextView) view.findViewById(R.id.text_view_username);

            view.setTag(holder);
        } else {
            holder = (ItemHolder) view.getTag();
        }

        holder.textUser.setText(data.get(position).username);

        return view;
    }

    private class ItemHolder {
        TextView textUser;

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