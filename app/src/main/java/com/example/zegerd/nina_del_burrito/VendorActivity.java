package com.example.zegerd.nina_del_burrito;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class VendorActivity extends AppCompatActivity {

    private TextView txtvw_name;
    private Button btn_itemMenu;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        // Init UI items
        txtvw_name = findViewById(R.id.textView2);
        btn_itemMenu = findViewById(R.id.button3);

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();
    }

    public void miMenu(View v) {
        Intent intent = new Intent(this, LoadingVendorFoodActivity.class);
        startActivity(intent);
    }

    public void signOut(View v) {
        mAuth.signOut();
        finish();
    }

    public void misPedidos(View v) {
        Intent intent = new Intent(this, LoadingVendorOrderActivity.class);
        startActivity(intent);
    }
}
