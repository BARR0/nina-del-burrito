package com.example.zegerd.nina_del_burrito.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.user_activities.CarritoActivity;

import java.util.ArrayList;

public class CarritoAdapter extends ItemAdapter {

    public CarritoAdapter(Activity activity) {
        super(activity);
    }

    public CarritoAdapter(Activity activity, ArrayList<Item> items) {
        super(activity, items);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(
                    R.layout.carrito_row,
                    null
            );
        }

        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView description = (TextView) view.findViewById(R.id.tv_description);
        TextView price = (TextView) view.findViewById(R.id.tv_price);
        ImageView itemImg = view.findViewById(R.id.iv_picture);
        Button badd = (Button) view.findViewById(R.id.buttonPlus);
        Button bsubstract = (Button) view.findViewById(R.id.buttonMinus);


        Item actual = items.get(i);
        name.setText(actual.getNombre());
        description.setText(actual.getDescripcion());
        price.setText("$" + actual.getPrecio());

        final View myV = view;

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!CarritoActivity.quantities.containsKey(items.get(i))) Log.d("HIIII", "NOOOOOO");
                int val = CarritoActivity.quantities[i];
//                if(val >= 10) return;
                CarritoActivity.quantities[i]++;
                TextView tv = (TextView) myV.findViewById(R.id.textViewQuantity);
                tv.setText((val + 1) + "");
            }
        });

        bsubstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int val = CarritoActivity.quantities[i];;
                if(val <= 1) return;
                CarritoActivity.quantities[i]--;
                TextView tv = (TextView) myV.findViewById(R.id.textViewQuantity);
                tv.setText((val - 1) + "");
            }
        });

        if (actual.getItemPicture() != null) {
            Glide.with(activity)
                    .load(actual.getItemPicture())
                    .thumbnail(0.3f)
                    .into(itemImg);
        }

        return view;
    }
}
