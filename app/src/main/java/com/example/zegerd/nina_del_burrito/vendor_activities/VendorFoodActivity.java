package com.example.zegerd.nina_del_burrito.vendor_activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.adapters.VendorItemAdapter;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.loading_activities.LoadingVendorFoodActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class VendorFoodActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDataReference;

    private ListView menu;
    private String vendorId;
    private FirebaseListAdapter<Item> firebaseAdapter;

    //public static final int ADD_FOOD_CODE = 1;
    //public static final int VIEW_FOOD_CODE = 2;

    public static final String FOOD_ITEM = "food_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_food);

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();
        mDataReference = FirebaseDatabase.getInstance().getReference("ItemsAll");
        vendorId = mAuth.getCurrentUser().getUid();

        // Init FireBase adapter params
        Query query = mDataReference.orderByChild("vendorid").equalTo(mAuth.getCurrentUser().getUid());
        FirebaseListOptions<Item> options = new FirebaseListOptions.Builder<Item>()
                .setLayout(R.layout.item_row)
                .setQuery(query, Item.class)
                .build();

        // Init Adapter
        firebaseAdapter = new FirebaseListAdapter<Item>(options) {
            @Override
            protected void populateView(View v, Item model, int position) {
                TextView itemName = v.findViewById(R.id.tv_name);
                TextView itemDesc = v.findViewById(R.id.tv_description);
                TextView itemPrice = v.findViewById(R.id.tv_price);
                ImageView itemImg = v.findViewById(R.id.iv_picture);
                Button b = (Button) v.findViewById(R.id.buttonRate);

                b.setVisibility(View.GONE);

                itemName.setText(model.getNombre());
                itemDesc.setText(model.getDescripcion());
                itemPrice.setText("" + model.getPrecio());
                if (model.getItemPicture() != null) {
                    Glide.with(VendorFoodActivity.this)
                            .load(model.getItemPicture())
                            .thumbnail(0.3f)
                            .into(itemImg);
                }
            }
        };

        // Init UI items
        menu = findViewById(R.id.listvw_menu);
        menu.setAdapter(firebaseAdapter);
        menu.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAdapter.stopListening();
    }

    public void toAddFood(View v) {
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Item item = (Item) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(this, ViewFoodDetailsActivity.class);
        intent.putExtra(FOOD_ITEM, item);
        startActivity(intent);
    }
}
