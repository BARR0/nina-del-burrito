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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by estef on 2/20/2018.
 */

public class ItemAdapter extends BaseAdapter {
    private Activity activity;
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
        ImageView itemPicture = view.findViewById(R.id.imageView2);

        Item actual = items.get(i);
        name.setText(actual.getNombre());
        description.setText(actual.getDescripcion());
        price.setText("$" + actual.getPrecio());

        if (actual.getItemPicture() != null) {
            StorageReference httpRef = FirebaseStorage.getInstance().getReferenceFromUrl(actual.getItemPicture());
            //Glide.with(activity).load(httpRef).thumbnail(0.5f).into(itemPicture);
            //http://javasampleapproach.com/android/firebase-storage-get-list-files-display-image-firebase-ui-database-android

        }

        return view;
    }
}
