package com.example.zegerd.nina_del_burrito.user_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zegerd.nina_del_burrito.MainActivity;
import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.adapters.CarritoAdapter;
import com.example.zegerd.nina_del_burrito.adapters.ItemAdapter;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.classes.Order;
import com.example.zegerd.nina_del_burrito.classes.User;
import com.example.zegerd.nina_del_burrito.user_activities.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CarritoActivity extends AppCompatActivity {
    private ListView lv_carrito;
    private Button b_pay;
    private FirebaseAuth mAuth;
    private User currentUser;
    public static int[] quantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();

        currentUser = (User) getIntent().getSerializableExtra(MainActivity.USER_DATA);

        quantities = new int[NavigationUserActivity.carrito.size()];
        for(int i = 0; i < quantities.length; ++i) {
            quantities[i] = 1;
        }

        lv_carrito = (ListView)findViewById(R.id.lv_carrito);
        b_pay = (Button)findViewById(R.id.b_pay);
        lv_carrito.setAdapter(new CarritoAdapter(this, NavigationUserActivity.carrito));
    }

    public void buy(View v){
        Intent intent = new Intent(this, PlaceOrderActivity.class);
        intent.putExtra(MainActivity.USER_DATA, currentUser);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(resultCode);
        finish();
    }
}
