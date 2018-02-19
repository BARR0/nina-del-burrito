package com.example.zegerd.nina_del_burrito;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public static final int VENDEDOR = 1;
    public static final int CLIENT = 0;

    // UI items
    private TextView txtvw_name, txtvw_greet;
    private Button btn_itemMenu;

    // Instance of FireBaseAuth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private User currentUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init UI items
        txtvw_name = findViewById(R.id.txtview_name);
        txtvw_greet = findViewById(R.id.txtview_greet);
        btn_itemMenu = findViewById(R.id.btn_itemMenu);

        // Init FireBaseAuth
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Handle null user
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        checkUserType(user);
    }

    private void checkUserType(final FirebaseUser user) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    currentUserInfo = dataSnapshot.getValue(User.class);
                    int userType = currentUserInfo.getRolId();
                    switch (userType)
                    {
                        case VENDEDOR:
                            showVendorScreen();
                            break;
                        case CLIENT:
                            showClientScreen();
                            break;
                        default:
                            Log.e("User rol type Error", "unknown user type: " + userType);
                            break;
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showVendorScreen() {
        txtvw_name.setText(currentUserInfo.getUsername());
        txtvw_greet.setText("Vendo Burritos");

        // Use Fragments for this later
        btn_itemMenu.setText("Agrega cosas atu tienda");
        btn_itemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showClientScreen() {
        txtvw_name.setText(currentUserInfo.getUsername());
        txtvw_greet.setText("Quiero burritos");

        // Use Fragments for this later
        btn_itemMenu.setText("Pide algo a tu salon");
        btn_itemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainUserActivity.class);
                startActivity(intent);
            }
        });
    }


    public void signOut(View v) {
        mAuth.signOut();
        finish();
    }
}
