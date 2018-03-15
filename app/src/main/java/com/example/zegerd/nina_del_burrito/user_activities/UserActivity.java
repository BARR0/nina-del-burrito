package com.example.zegerd.nina_del_burrito.user_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.adapters.AllItemAdapter;
import com.example.zegerd.nina_del_burrito.adapters.ItemAdapter;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private FirebaseAuth mAuth;
    private ListView lv_items;
    private ItemAdapter itemAdapter;
    public static ArrayList<Item> carrito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        lv_items = (ListView)findViewById(R.id.lv_menu);
        itemAdapter = new AllItemAdapter(this);
        lv_items.setAdapter(itemAdapter);
        lv_items.setOnItemClickListener(this);

        carrito = new ArrayList<>();
    }

    public void signOut(View v) {
        mAuth.signOut();
        finish();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Item item = (Item)itemAdapter.getItem(i);
        Toast.makeText(this, item.toString() + " en el carrito.", Toast.LENGTH_LONG).show();
        if (!carrito.contains(item))
            carrito.add(item);

    }

    public void viewCarrito(View view){
        Intent intent = new Intent(this, CarritoActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == CarritoActivity.RESULT_BOUGHT){
            carrito = new ArrayList<>();
        }

    }
}
