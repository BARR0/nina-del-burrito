package com.example.zegerd.nina_del_burrito;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class LoadingVendorFoodActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_vendor_food);

        mAuth = FirebaseAuth.getInstance();

        VendorItemAdapter itemAdapter = new VendorItemAdapter(this, mAuth.getCurrentUser().getUid());
    }
}
