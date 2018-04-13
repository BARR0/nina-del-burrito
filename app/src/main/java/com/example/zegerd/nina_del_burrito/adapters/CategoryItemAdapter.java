package com.example.zegerd.nina_del_burrito.adapters;

import android.app.Activity;
import android.util.Log;

import com.example.zegerd.nina_del_burrito.classes.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CategoryItemAdapter extends ItemAdapter {
    public CategoryItemAdapter(Activity activity, final String category) {
        super(activity);
        FirebaseDatabase.getInstance().getReference().child("ItemsAll").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Item tmp = child.getValue(Item.class);
                    List<String> cats = tmp.getCategories();
                    if (cats == null) continue;
                    Log.d("CATS", cats.toString());
                    if (tmp.isDisponible() && cats.contains(category))
                        items.add(tmp);
                    Log.d("ITEM", child.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}