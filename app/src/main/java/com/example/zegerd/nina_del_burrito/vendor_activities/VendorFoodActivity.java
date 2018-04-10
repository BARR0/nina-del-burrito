package com.example.zegerd.nina_del_burrito.vendor_activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.adapters.VendorItemAdapter;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.loading_activities.LoadingVendorFoodActivity;
import com.google.firebase.auth.FirebaseAuth;

public class VendorFoodActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseAuth mAuth;

    private ListView menu;
    private String vendorId;
    private VendorItemAdapter itemAdapter;

    public static final int ADD_FOOD_CODE = 1;
    public static final int VIEW_FOOD_CODE = 2;

    public static final String FOOD_ITEM = "food_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_food);

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();
        vendorId = mAuth.getCurrentUser().getUid();

        itemAdapter = LoadingVendorFoodActivity.item_Adapter;

        // Init UI items
        menu = findViewById(R.id.listvw_menu);
        menu.setAdapter(itemAdapter);
        menu.setOnItemClickListener(this);
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
        Item item = (Item) itemAdapter.getItem(i);
        Intent intent = new Intent(this, ViewFoodDetailsActivity.class);
        intent.putExtra(FOOD_ITEM, item);
        startActivityForResult(intent, VIEW_FOOD_CODE);
    }
}
