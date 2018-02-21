package com.example.zegerd.nina_del_burrito;

import android.app.Activity;
import android.util.Log;

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
                    for (DataSnapshot child2: child.getChildren()) {
                        Item tmp = child2.getValue(Item.class);
                        if (tmp.isDisponible())
                            items.add(tmp);
                        Log.d("ITEM", child.toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
