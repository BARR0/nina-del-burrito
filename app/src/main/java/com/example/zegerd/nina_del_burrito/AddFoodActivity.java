package com.example.zegerd.nina_del_burrito;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFoodActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private EditText name, desc, precio;
    private Button addFood;

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
    }


    public void addFood(View v){
        // TODO authenticate empty fields

        String itemName = name.getText().toString();
        String itemDesc = desc.getText().toString();
        float itemPrice = Float.parseFloat(precio.getText().toString());

        Item food = new Item(itemName, itemDesc, itemPrice);

        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference currentItemDB = FirebaseDatabase.getInstance()
                .getReference()
                .child("Items")
                .child(userId)
                .child(food.getNombre());
        currentItemDB.setValue(food);

        Toast.makeText(this, "Producto añadido", Toast.LENGTH_SHORT).show();
    }

    public void endAddFoof(View v) {
        Intent result = new Intent();
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent result = new Intent();
        setResult(RESULT_OK, result);
        finish();
    }
}
