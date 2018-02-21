package com.example.zegerd.nina_del_burrito;

import android.app.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by estef on 2/20/2018.
 */

public class AllItemAdapter extends ItemAdapter {
    public AllItemAdapter(Activity activity) {
        super(activity);
        FirebaseDatabase.getInstance().getReference().child("Items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Item tmp = dataSnapshot.getValue(Item.class);
                    if (tmp.isDisponible())
                        items.add(tmp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
