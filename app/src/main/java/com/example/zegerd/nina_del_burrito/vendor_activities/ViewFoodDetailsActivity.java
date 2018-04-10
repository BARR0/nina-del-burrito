package com.example.zegerd.nina_del_burrito.vendor_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ViewFoodDetailsActivity extends AppCompatActivity {

    private TextView name, price, description;
    private CheckBox availabelBox;
    private ImageView foodImage;

    private FirebaseAuth mAuth;
    private DatabaseReference itemRef;

    private Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_details);

        // Init UI elements
        name = findViewById(R.id.txtvw_detailName);
        price = findViewById(R.id.txtvw_detailPrice);
        description = findViewById(R.id.txtvw_detalDescription);
        availabelBox = findViewById(R.id.checkBox_detailAvailable);
        foodImage = findViewById(R.id.imageViewFood);

        // Get selected item
        Intent intent = getIntent();
        currentItem = (Item) intent.getSerializableExtra(VendorFoodActivity.FOOD_ITEM);

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();
        itemRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("ItemsAll")
                .child(mAuth.getCurrentUser().getUid() + currentItem.getNombre());

        // Update UI elements
        name.setText(currentItem.getNombre());
        price.setText("" + currentItem.getPrecio());
        description.setText(currentItem.getDescripcion());
        availabelBox.setChecked(currentItem.isDisponible());

        // Watch later
        //https://www.youtube.com/watch?v=ytoOw5ZDQkU
    }

    public void deleteFood(View v) {
        // delete the register in db
        itemRef.setValue(null);

        Toast.makeText(this, "Producto eliminado de la tienda", Toast.LENGTH_SHORT).show();
        this.goBack();
    }

    public void updateFood(View v) {
        // update the register in db
        /*
        String nameItem = currentItem.getNombre();
        String descItem = currentItem.getDescripcion();
        float priceItem = currentItem.getPrecio();
        String vendorItem = currentItem.getVendorid();
        List<String> categItem = currentItem.getCategories();
        */
        //Item newItem = new Item(nameItem, descItem, priceItem, vendorItem, categItem);

        boolean availableItem = availabelBox.isChecked();
        currentItem.setDisponible(availableItem);

        itemRef.setValue(currentItem);
        this.goBack();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.goBack();
    }

    private void goBack() {
        Intent result = new Intent();
        setResult(RESULT_OK, result);
        finish();
    }
}
