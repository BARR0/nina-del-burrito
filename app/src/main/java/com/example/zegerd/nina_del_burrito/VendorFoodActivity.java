package com.example.zegerd.nina_del_burrito;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VendorFoodActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ListView menu;
    private String vendorId;
    private VendorItemAdapter itemAdapter;

    public static final int ADD_FOOD_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_food);

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();
        vendorId = mAuth.getCurrentUser().getUid();

        // Init UI items
        menu = findViewById(R.id.listvw_menu);
        menu.setAdapter(LoadingVendorFoodActivity.item_Adapter);
    }

    public void toAddFood(View v) {
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivityForResult(intent, ADD_FOOD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FOOD_CODE && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(this, LoadingVendorFoodActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
