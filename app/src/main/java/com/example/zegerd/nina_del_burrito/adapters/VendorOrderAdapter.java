package com.example.zegerd.nina_del_burrito.adapters;

import android.app.Activity;

import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.classes.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Carlos Rueda on 21/02/2018.
 */

public class VendorOrderAdapter extends OrderAdapter {
    public VendorOrderAdapter(Activity activity, String userId) {
        super(activity);
        FirebaseDatabase.getInstance().getReference().child("ClientOrders").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            for (DataSnapshot child2: child.getChildren()) {
                                Order tmp = child2.getValue(Order.class);
                                orders.add(tmp);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
