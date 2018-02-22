package com.example.zegerd.nina_del_burrito;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class VendorOrdersActivity extends AppCompatActivity {
    private ListView pedidos;
    private FirebaseAuth mAuth;
    private String vendorId;
    private VendorOrderAdapter orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_orders);
        pedidos = findViewById(R.id.lv_pedidos);
        mAuth = FirebaseAuth.getInstance();
        vendorId = mAuth.getCurrentUser().getUid();
        orders = LoadingVendorOrderActivity.order_Adapter;

        pedidos.setAdapter(orders);
    }


}
