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

    // Instance of FireBaseAuth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private User currentUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init UI items

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
        Intent intent = new Intent(MainActivity.this, VendorActivity.class);
        startActivity(intent);
        finish();
    }

    private void showClientScreen() {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        startActivity(intent);
        finish();
    }


}
