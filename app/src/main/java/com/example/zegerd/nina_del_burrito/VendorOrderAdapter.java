package com.example.zegerd.nina_del_burrito;

import android.app.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Carlos Rueda on 21/02/2018.
 */

public class VendorOrderAdapter extends ItemAdapter {
    public VendorOrderAdapter(Activity activity, String userId) {
        super(activity);
        FirebaseDatabase.getInstance().getReference().child("Orders").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Item tmp = child.getValue(Item.class);
                            items.add(tmp);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
