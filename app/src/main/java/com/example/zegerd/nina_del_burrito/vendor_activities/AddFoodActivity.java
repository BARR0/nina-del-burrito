package com.example.zegerd.nina_del_burrito.vendor_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFoodActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private EditText name, desc, precio, category;

    private ListView listViewCategories;
    private List<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        // Init FB stuff
        mAuth = FirebaseAuth.getInstance();

        //Init Activity components
        name = findViewById(R.id.editTxt_foodNombre);
        desc = findViewById(R.id.editTxt_foodDesc);
        precio = findViewById(R.id.editTxt_foodPrecio);
        category = findViewById(R.id.editTextCategory);

        listViewCategories = (ListView)findViewById(R.id.listViewCategory);
        categories = new ArrayList<>();
        listViewCategories.setAdapter(new ArrayAdapter<String>(this, R.layout.category_layout, R.id.textViewCategoryRow, categories));
    }


    public void addFood(View v){
        if (categories.size() < 1){
            Toast.makeText(this, "Please add at least one category.", Toast.LENGTH_SHORT).show();
            return;
        }

        String itemName = name.getText().toString();
        String itemDesc = desc.getText().toString();
        String itemPriceText = precio.getText().toString();
        if(itemPriceText.matches("") || itemName.matches("") || itemPriceText.matches("")){
            Toast.makeText(this, "Hay campos vacíos", Toast.LENGTH_SHORT).show();
            return;
        }
        float itemPrice = Float.parseFloat(itemPriceText);

        Map<String, Boolean> cat = new HashMap<>();
        for (String i : categories) cat.put(i, true);
        Item food = new Item(itemName, itemDesc, itemPrice, mAuth.getCurrentUser().getUid(), cat);

        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference currentItemDB = FirebaseDatabase.getInstance()
                .getReference()
                .child("ItemsAll")
                .child(userId + food.getNombre());
        currentItemDB.setValue(food);
        name.setText("");
        desc.setText("");
        precio.setText("");
        categories = new ArrayList<>();
        listViewCategories.setAdapter(new ArrayAdapter<String>(this, R.layout.category_layout, R.id.textViewCategoryRow, categories));
        Toast.makeText(this, "Producto " + itemName + " añadido", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    public void addCategory(View v){
        String s = category.getText().toString();
        //Toast.makeText(this, "Please: " + s, Toast.LENGTH_SHORT).show();
        if (s == null || s == "")
            return;
        if (categories.contains(s))
            return;
        category.setText("");
        categories.add(s);
        listViewCategories.setAdapter(new ArrayAdapter<String>(this, R.layout.category_layout, R.id.textViewCategoryRow, categories));
    }
}
