package com.example.zegerd.nina_del_burrito;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class VendorActivity extends AppCompatActivity {

    private TextView txtvw_name, txtvw_greet;
    private Button btn_itemMenu;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);


        txtvw_name = findViewById(R.id.textView2);
        txtvw_greet = findViewById(R.id.textView4);
        btn_itemMenu = findViewById(R.id.button3);

       mAuth = FirebaseAuth.getInstance();
    }



    public void signOut(View v) {
        mAuth.signOut();
        finish();
    }
}
