package com.example.zegerd.nina_del_burrito.vendor_activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.adapters.VendorOrderAdapter;
import com.example.zegerd.nina_del_burrito.classes.Order;
import com.example.zegerd.nina_del_burrito.loading_activities.LoadingVendorFoodActivity;
import com.example.zegerd.nina_del_burrito.loading_activities.LoadingVendorOrderActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class VendorOrdersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView pedidos;
    private FirebaseAuth mAuth;
    private String vendorId;
    private VendorOrderAdapter orders;

    public static final int VIEW_ORDER_CODE = 2;

    public static final String ORDER_NAME = "order_name";
    //public static final String ORDER_QUANTITY = "order_quantity";
    //public static final String ORDER_TIME = "order_time";
    //public static final String ORDER_PLACE = "order_plane";
    //public static final String ORDER_CLIENT = "order_client";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_orders);
        pedidos = findViewById(R.id.lv_pedidos);
        mAuth = FirebaseAuth.getInstance();
        vendorId = mAuth.getCurrentUser().getUid();
        orders = LoadingVendorOrderActivity.order_Adapter;

        pedidos.setAdapter(orders);
        pedidos.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Order order = (Order) orders.getItem(i);
        Intent intent = new Intent(this, ViewOrderActivity.class);
        intent.putExtra(ORDER_NAME, order);
        startActivityForResult(intent, VIEW_ORDER_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VIEW_ORDER_CODE && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(this, LoadingVendorOrderActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
