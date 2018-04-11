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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zegerd.nina_del_burrito.MainActivity;
import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.adapters.ItemAdapter;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.classes.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class NavigationUserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private FirebaseAuth mAuth;
    private ListView lv_items;
    private ItemAdapter itemAdapter;
    private User currentUser;

    public static ArrayList<Item> carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar); // Enables settings button

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

        initContent();

        // Change header info
        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.textView_header_name);
        TextView mail = header.findViewById(R.id.textView_header_mail);
        mail.setText(currentUser.getUsername());
        if (currentUser.getRolId() == 1) {
            name.setText("Vendedor");
        } else name.setText("Comprador");

    }

    private void initContent() {
        mAuth = FirebaseAuth.getInstance();
        lv_items = findViewById(R.id._dynamic_listView);
        //itemAdapter = new AllItemAdapter(this);
        itemAdapter = MainActivity.allItemAdapter;
        lv_items.setAdapter(itemAdapter);
        lv_items.setOnItemClickListener(this);

        carrito = new ArrayList<>();
        currentUser = (User) getIntent().getSerializableExtra(MainActivity.USER_DATA);
    }

    public void signOut(View v) {
        this.signOut();
    }

    private void signOut() {
        mAuth.signOut();
        finish();
    }

    public void viewCarrito(View view){
        viewCarrito();
    }

    private void viewCarrito() {
        Intent intent = new Intent(this, CarritoActivity.class);
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

        if (id == R.id.nav_profile) {
            Toast.makeText(this, "Mi perifl", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_cart) {
            viewCarrito();
        } else if (id == R.id.nav_options) {
            Toast.makeText(this, "Opciones", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_signout) {
            this.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Item item = (Item)itemAdapter.getItem(i);
        Toast.makeText(this, item.toString() + " en el carrito.", Toast.LENGTH_LONG).show();
        if (!carrito.contains(item))
            carrito.add(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == CarritoActivity.RESULT_BOUGHT){
            carrito = new ArrayList<>();
        }
    }
}
