package com.example.zegerd.nina_del_burrito;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Zegerd on 2/20/2018.
 */

public class VendorItemAdapter extends ItemAdapter {
    public VendorItemAdapter(Activity activity, String userId) {
        super(activity);
        FirebaseDatabase.getInstance().getReference().child("Items").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Item tmp = child.getValue(Item.class);
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
