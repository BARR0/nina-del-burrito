package com.example.zegerd.nina_del_burrito.classes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zegerd.nina_del_burrito.R;

public class ImgViewHolder extends RecyclerView.ViewHolder {
    public TextView itemName;
    public TextView itemDesc;
    public TextView itemPrice;
    public ImageView itemImg;


    public ImgViewHolder(View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.textView_itemName);
        itemDesc = itemView.findViewById(R.id.textView_itemDesc);
        itemPrice = itemView.findViewById(R.id.textView_itemPrice);
        itemImg = itemView.findViewById(R.id.imgView_item);
    }
}
