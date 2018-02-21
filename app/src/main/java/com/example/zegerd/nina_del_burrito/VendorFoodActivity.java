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
    private ArrayList<String> source;

    public static final String ITEMS_FB = "Items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_food);

        // Init FB stuff
        mAuth = FirebaseAuth.getInstance();

        // Init UI items
        menu = findViewById(R.id.listvw_menu);

        // Init array list
        source = new ArrayList<>();

        // Read database
        FirebaseDatabase.getInstance().getReference().child(ITEMS_FB).child(mAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String temp = snapshot.getValue(Item.class).getDescripcion();
                            source.add(temp);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
        });

        // Init Adapter
        ArrayAdapter itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, source);

        // Set Adapter
        menu.setAdapter(itemAdapter);
    }



    public void toAddFood(View v) {
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivity(intent);
    }

    private void updateList() {

    }
}
