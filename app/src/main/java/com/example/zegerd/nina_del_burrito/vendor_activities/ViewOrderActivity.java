package com.example.zegerd.nina_del_burrito.vendor_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.classes.Order;
import com.example.zegerd.nina_del_burrito.loading_activities.LoadingVendorOrderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.GregorianCalendar;

public class ViewOrderActivity extends AppCompatActivity {

    private TextView tv_name, tv_quantity, tv_desc, tv_date, tv_client;
    private String orderKey;
    private String orderClientId;
    private FirebaseAuth mAuth;
    private Order tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        mAuth = FirebaseAuth.getInstance();

        tv_name = findViewById(R.id.txtvw_orderName);
        tv_quantity = findViewById(R.id.txtvw_orderQuant);
        tv_desc = findViewById(R.id.txtvw_orderPlace);
        tv_date = findViewById(R.id.txtvw_orderTime);
        tv_client = findViewById(R.id.txtvw_orderClient);

        Intent intent = getIntent();
        tmp = (Order) intent.getSerializableExtra(VendorOrdersActivity.ORDER_NAME);

        orderKey = tmp.getKey();
        orderClientId = tmp.getClientId();
        tv_name.setText(tmp.getItemName());
        tv_quantity.setText("" + tmp.getQuantity());
        tv_desc.setText(tmp.getDescription());
        tv_date.setText("" + tmp.getHour() + ":" + tmp.getMinute());
        tv_client.setText(tmp.getClientName());
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent result = new Intent();
        setResult(RESULT_OK, result);
        finish();
    }

    public void deleteOrder(View v) {
        // Delete from data base this order
        DatabaseReference currentOrder = FirebaseDatabase.getInstance()
                .getReference()
                .child("ClientOrders")
                .child(mAuth.getCurrentUser().getUid())
                .child(orderClientId)
                .child(orderKey);
        currentOrder.setValue(null);

        Intent result = new Intent();
        setResult(RESULT_OK, result);
        Toast.makeText(this, "Orden Completada", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void viewLocation(View v){
        Intent intent = new Intent(this, ViewOrderLocationActivity.class);
        intent.putExtra("lat", tmp.getLat());
        intent.putExtra("lng", tmp.getLng());
        startActivity(intent);
    }
}
