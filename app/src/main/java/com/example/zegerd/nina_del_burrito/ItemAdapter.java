package com.example.zegerd.nina_del_burrito;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        Item actual = items.get(i);
        name.setText(actual.getNombre());
        description.setText(actual.getDescripcion());
        price.setText(actual.getPrecio() + "");

        return view;
    }
}
