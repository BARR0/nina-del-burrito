package com.example.zegerd.nina_del_burrito.user_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zegerd.nina_del_burrito.MainActivity;
import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.adapters.ItemAdapter;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.classes.Order;
import com.example.zegerd.nina_del_burrito.classes.User;
import com.example.zegerd.nina_del_burrito.user_activities.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CarritoActivity extends AppCompatActivity {
    public static final int RESULT_BOUGHT = 1;
    public static final int RESULT_NOT_BOUGHT = 0;
    private ListView lv_carrito;
    private Button b_pay;
    private int response;
    private FirebaseAuth mAuth;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();

        currentUser = (User) getIntent().getSerializableExtra(MainActivity.USER_DATA);

        lv_carrito = (ListView)findViewById(R.id.lv_carrito);
        b_pay = (Button)findViewById(R.id.b_pay);
        lv_carrito.setAdapter(new ItemAdapter(this, NavigationUserActivity.carrito));
        response = RESULT_NOT_BOUGHT;
        setResult(response);
    }

    public void buy(View v){
        response = RESULT_BOUGHT;
        setResult(response);
        // TODO change this with the quantity chosen by the user
        int quantity = 1;
        for (Item item: NavigationUserActivity.carrito) {
            setOrders(item, quantity);
        }
        Toast.makeText(this, "Orden Enviada", Toast.LENGTH_SHORT).show();
    }

    private void setOrders(Item item, int quantity){
        String vendorId = item.getVendorid();
        String clientId = mAuth.getCurrentUser().getUid();

        DatabaseReference currentItemDB = FirebaseDatabase.getInstance()
                .getReference()
                .child("ClientOrders")
                .child(vendorId)
                .child(clientId);
        // push() generates an unique id
        String key = currentItemDB.push().getKey();
        Order order = new Order(quantity,
                "En mi casa",
                new Date(),
                vendorId + item.getNombre(),
                item.getNombre(),
                key,
                currentUser.getUsername(),
                clientId);

        currentItemDB.child(key).setValue(order);
    }
}
