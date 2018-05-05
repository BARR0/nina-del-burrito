package com.example.zegerd.nina_del_burrito.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.user_activities.NavigationUserActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by estef on 2/20/2018.
 */

public class ItemAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Item> items;

    public ItemAdapter(Activity activity) {
        this.activity = activity;
        this.items = new ArrayList<>();
    }

    public ItemAdapter(Activity activity, ArrayList<Item> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(
                    R.layout.item_row,
                    null
            );
        }

        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView description = (TextView) view.findViewById(R.id.tv_description);
        TextView price = (TextView) view.findViewById(R.id.tv_price);
        ImageView itemImg = view.findViewById(R.id.iv_picture);

        Item actual = items.get(i);
        name.setText(actual.getNombre());
        description.setText(actual.getDescripcion());
        price.setText("$" + actual.getPrecio());

        if (actual.getItemPicture() != null) {
            Glide.with(activity)
                    .load(actual.getItemPicture())
                    .thumbnail(0.3f)
                    .into(itemImg);
        }

        return view;
    }
}
