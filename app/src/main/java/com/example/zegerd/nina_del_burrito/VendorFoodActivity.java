package com.example.zegerd.nina_del_burrito;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_food);

        // Init FB stuff
        mAuth = FirebaseAuth.getInstance();

        // Init UI items
        menu = findViewById(R.id.listvw_menu);

        updateList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateList();
    }

    public void toAddFood(View v) {
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivity(intent);
    }

    private void updateList() {
        // Set Adapter
        VendorItemAdapter itemAdapter = new VendorItemAdapter(this, mAuth.getCurrentUser().getUid());
        menu.setAdapter(itemAdapter);
    }
}
