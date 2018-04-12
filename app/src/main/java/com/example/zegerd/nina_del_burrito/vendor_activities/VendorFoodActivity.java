package com.example.zegerd.nina_del_burrito.vendor_activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.adapters.VendorItemAdapter;
import com.example.zegerd.nina_del_burrito.classes.ImgViewHolder;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.loading_activities.LoadingVendorFoodActivity;
import com.firebase.ui.database.ClassSnapshotParser;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VendorFoodActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDataReference;

    private ListView menu;
    private RecyclerView foodMenu;
    private String vendorId;
    private VendorItemAdapter itemAdapter;
    private FirebaseRecyclerAdapter<Item, ImgViewHolder> mAdapter;
    private FirebaseListAdapter<Item> m2Adapter;

    public static final int ADD_FOOD_CODE = 1;
    public static final int VIEW_FOOD_CODE = 2;

    public static final String FOOD_ITEM = "food_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_food);

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();
        mDataReference = FirebaseDatabase.getInstance().getReference("ItemsAll");
        vendorId = mAuth.getCurrentUser().getUid();

        itemAdapter = LoadingVendorFoodActivity.item_Adapter;

        // Init UI items
        menu = findViewById(R.id.listvw_menu);

        // Populate Recycler View
        Query query = mDataReference.orderByChild("vendorid").equalTo(mAuth.getCurrentUser().getUid());
        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();

        FirebaseListOptions<Item> options2 = new FirebaseListOptions.Builder<Item>()
                .setLayout(R.layout.item_image)
                .setQuery(query, Item.class)
                .build();
        m2Adapter = new FirebaseListAdapter<Item>(options2) {
            @Override
            protected void populateView(View v, Item model, int position) {
                TextView itemName = v.findViewById(R.id.textView_itemName);
                TextView itemDesc = v.findViewById(R.id.textView_itemDesc);
                TextView itemPrice = v.findViewById(R.id.textView_itemPrice);
                ImageView itemImg = v.findViewById(R.id.imgView_item);

                itemName.setText(model.getNombre());
                itemDesc.setText(model.getDescripcion());
                itemPrice.setText("" + model.getPrecio());
                if (model.getItemPicture() != null) {
                    Glide.with(VendorFoodActivity.this)
                            .load(model.getItemPicture())
                            .into(itemImg);
                }
            }
        };

        menu.setAdapter(m2Adapter);
        menu.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        m2Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        m2Adapter.stopListening();
    }

    public void toAddFood(View v) {
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivityForResult(intent, ADD_FOOD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(this, LoadingVendorFoodActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Item item = (Item) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(this, ViewFoodDetailsActivity.class);
        intent.putExtra(FOOD_ITEM, item);
        startActivityForResult(intent, VIEW_FOOD_CODE);
    }
}
