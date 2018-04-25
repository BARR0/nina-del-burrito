package com.example.zegerd.nina_del_burrito.user_activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zegerd.nina_del_burrito.MainActivity;
import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.adapters.CategoryItemAdapter;
import com.example.zegerd.nina_del_burrito.adapters.ItemAdapter;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.classes.User;
import com.example.zegerd.nina_del_burrito.vendor_activities.VendorFoodActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigationUserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    public final String[] CATEGORIES = {"todo", "desayuno", "comida", "postre", "cena"};

    // FireBase Objects
    private FirebaseAuth mAuth;
    private DatabaseReference mDataReference;
    private FirebaseListAdapter<Item> firebaseMainAdapter;
    private FirebaseListAdapter<Item> currentAdapter;

    private ListView lv_items;
    private ItemAdapter itemAdapter;
    private User currentUser;
    private Spinner spinnerCategory;
    private Map<String, FirebaseListAdapter<Item>> FBadapters;

    public static ArrayList<Item> carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar); // Enables settings button

        /* Navigation stuff */
        // Round button from the lower right corner
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCarrito();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /* End Navigation stuff */

        // Our stuff
        initContent();
        changeHeaderInfo(navigationView);
        initDefaultAdaptersCategories();

        // Spinner change adpater logic
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        List<String> cats = Arrays.asList(CATEGORIES);
        final SpinnerAdapter adapter = new ArrayAdapter<String>(this, R.layout.catergory_row, R.id.textViewCategory, cats);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentAdapter.stopListening();
                String s = adapter.getItem(i).toString();
                if (s.equals(CATEGORIES[0])) {
                    currentAdapter = firebaseMainAdapter;
                }
                else {
                    currentAdapter = FBadapters.get(s);
                }
                lv_items.setAdapter(currentAdapter);
                currentAdapter.startListening();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                lv_items.setAdapter(currentAdapter);
            }
        });

    }

    private void initContent() {
        // FireBase stuff
        mAuth = FirebaseAuth.getInstance();
        mDataReference = FirebaseDatabase.getInstance().getReference("ItemsAll");

        // UI Stuff
        lv_items = findViewById(R.id._dynamic_listView);

        // Main adapter init
        // - itemAdapter = MainActivity.allItemAdapter;
        Query query = mDataReference.orderByChild("disponible").equalTo(true);
        firebaseMainAdapter = initFirebaseAdapter(query);
        lv_items.setAdapter(firebaseMainAdapter);
        lv_items.setOnItemClickListener(this);
        currentAdapter = firebaseMainAdapter;

        // Other inits
        carrito = new ArrayList<>();
        currentUser = (User) getIntent().getSerializableExtra(MainActivity.USER_DATA);
    }

    private void initDefaultAdaptersCategories() {
        FBadapters = new HashMap<String, FirebaseListAdapter<Item>>();
        for (String s : CATEGORIES) {
            Query tempQuery = mDataReference.orderByChild("categories/" + s).equalTo(true);
            FirebaseListAdapter<Item> FBtempAdapter = initFirebaseAdapter(tempQuery);
            FBadapters.put(s, FBtempAdapter);
        }
    }

    private FirebaseListAdapter<Item> initFirebaseAdapter(Query query) {
        // Init firebase default options
        FirebaseListOptions<Item> options = new FirebaseListOptions.Builder<Item>()
                .setLayout(R.layout.item_row)
                .setQuery(query, Item.class)
                .build();

        // Init adpater
        FirebaseListAdapter<Item> FBAdapter = new FirebaseListAdapter<Item>(options) {
            @Override
            protected void populateView(View v, Item model, int position) {
                TextView itemName = v.findViewById(R.id.tv_name);
                TextView itemDesc = v.findViewById(R.id.tv_description);
                TextView itemPrice = v.findViewById(R.id.tv_price);
                ImageView itemImg = v.findViewById(R.id.iv_picture);

                itemName.setText(model.getNombre());
                itemDesc.setText(model.getDescripcion());
                itemPrice.setText("" + model.getPrecio());
                if (model.getItemPicture() != null) {
                    Glide.with(NavigationUserActivity.this)
                            .load(model.getItemPicture())
                            .into(itemImg);
                }
            }
        };

        return FBAdapter;
    }

    private void changeHeaderInfo(NavigationView navigationView) {
        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.textView_header_name);
        TextView mail = header.findViewById(R.id.textView_header_mail);
        mail.setText(currentUser.getUsername());
        if (currentUser.getRolId() == 1) {
            name.setText("Vendedor");
        } else name.setText("Comprador");
    }

    public void signOut(View v) {
        this.signOut();
    }

    private void signOut() {
        currentAdapter.stopListening();
        mAuth.signOut();
        finish();
    }

    public void viewCarrito(View view){
        viewCarrito();
    }

    private void viewCarrito() {
        Intent intent = new Intent(this, CarritoActivity.class);
        intent.putExtra(MainActivity.USER_DATA, currentUser);
        startActivityForResult(intent,0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            viewCarrito();
        } else if (id == R.id.nav_signout) {
            this.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Item item = (Item) adapterView.getItemAtPosition(i);
        Toast.makeText(this, item.toString() + " en el carrito.", Toast.LENGTH_LONG).show();
        if (!carrito.contains(item))
            carrito.add(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == PlaceOrderActivity.RESULT_BOUGHT){
            carrito = new ArrayList<>();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentAdapter.stopListening();
    }
}
