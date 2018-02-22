package com.example.zegerd.nina_del_burrito;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CarritoActivity extends AppCompatActivity {
    public static final int RESULT_BOUGHT = 1;
    public static final int RESULT_NOT_BOUGHT = 0;
    private ListView lv_carrito;
    private Button b_pay;
    private int response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        lv_carrito = (ListView)findViewById(R.id.lv_carrito);
        b_pay = (Button)findViewById(R.id.b_pay);
        lv_carrito.setAdapter(new ItemAdapter(this, UserActivity.carrito));
        response = RESULT_NOT_BOUGHT;
        setResult(response);
    }

    public void buy(View v){
        response = RESULT_BOUGHT;
        setResult(response);
        for (Item item: UserActivity.carrito) {
            setOrders(item);
        }
    }

    private void setOrders(Item item){
        String userId = item.getVendorid();
        DatabaseReference currentItemDB = FirebaseDatabase.getInstance()
                .getReference()
                .child("Orders")
                .child(userId)
                .child(item.getNombre());
        currentItemDB.setValue(item);
    }
}
