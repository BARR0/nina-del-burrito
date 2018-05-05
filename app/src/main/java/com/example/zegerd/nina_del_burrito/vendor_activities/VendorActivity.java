package com.example.zegerd.nina_del_burrito.vendor_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.loading_activities.LoadingVendorFoodActivity;
import com.example.zegerd.nina_del_burrito.loading_activities.LoadingVendorOrderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VendorActivity extends AppCompatActivity {

    private TextView txtvw_name, tvrating;
    private Button btn_itemMenu;
    private FirebaseAuth mAuth;

    private double rating, productquantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        // Init UI items
        txtvw_name = findViewById(R.id.textView2);
        btn_itemMenu = findViewById(R.id.button3);
        tvrating = findViewById(R.id.textViewRating);
        rating = 0.0;
        productquantity = 0.0;
        getVendorRating();

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();
    }

    public void miMenu(View v) {
        Intent intent = new Intent(this, VendorFoodActivity.class);
        startActivity(intent);
    }

    public void signOut(View v) {
        mAuth.signOut();
        finish();
    }

    public void misPedidos(View v) {
        Intent intent = new Intent(this, LoadingVendorOrderActivity.class);
        startActivity(intent);
    }

    private void getVendorRating(){
        final String vendorId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("ItemsAll")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Item tmp = child.getValue(Item.class);
                            if (tmp.getVendorid().equals(vendorId)) {
                                productquantity += 1.0;
                                rating = rating + (tmp.getRating() - rating) / productquantity;
                                tvrating.setText("" + (int)(rating * 100.0));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
