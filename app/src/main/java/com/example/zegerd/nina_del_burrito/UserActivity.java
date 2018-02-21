package com.example.zegerd.nina_del_burrito;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity implements AdapterView.OnClickListener {
    private FirebaseAuth mAuth;
    private ListView lv_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        lv_items = (ListView)findViewById(R.id.lv_menu);
        lv_items.setAdapter(new AllItemAdapter(this));
//        lv_items.getOnItemClickListener(this);
    }

    public void signOut(View v) {
        mAuth.signOut();
        finish();
    }

    @Override
    public void onClick(View view) {

    }
}
