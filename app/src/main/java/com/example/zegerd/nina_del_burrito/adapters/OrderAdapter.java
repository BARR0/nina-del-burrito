package com.example.zegerd.nina_del_burrito.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.classes.Order;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    private Activity activity;
    protected ArrayList<Order> orders;

    public OrderAdapter(Activity activity) {
        this.activity = activity;
        this.orders = new ArrayList<>();
    }

    public OrderAdapter(Activity activity, ArrayList<Order> items){
        this.activity = activity;
        this.orders = items;
    }

    @Override
    public int getCount() {
        return this.orders.size();
    }

    @Override
    public Object getItem(int i) {
        return this.orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(
                    R.layout.order_row,
                    null
            );
        }

        TextView name = (TextView) view.findViewById(R.id.order_nameToChange);
        TextView quantity = (TextView) view.findViewById(R.id.order_quantityToChange);

        Order actual = orders.get(i);
        name.setText(actual.getItemName());
        quantity.setText("" + actual.getQuantity());

        return view;
    }
}
